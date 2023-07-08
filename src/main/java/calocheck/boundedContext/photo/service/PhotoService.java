package calocheck.boundedContext.photo.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.photo.config.S3ConfigProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private final S3ConfigProperties s3ConfigProperties;
    private final ImageAnnotatorSettings visionAPISettings;
    private final AmazonS3 amazonS3;

    // upload local file
    @Transactional
    public String photoUpload(MultipartFile file) throws IOException {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);

        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = "foodImage/" + storeFileName;

        InputStream inputStream = file.getInputStream();

        String bucketName = s3ConfigProperties.getBucket();
        String endPoint = s3ConfigProperties.getEndPoint();

        amazonS3.putObject(new PutObjectRequest(bucketName, key, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return endPoint + "/%s/%s".formatted(bucketName, key);
    }

    public RsData<String> isImgFile(String fileName) {

        //확장자 추출
        String fileExtension = StringUtils.getFilenameExtension(fileName);

        if (fileExtension == null) {
            return RsData.of("S-7", "없어도 생성이 가능합니다.");
        } else if (fileExtension.equals("jpg") || fileExtension.equals("jpeg")
                || fileExtension.equals("png") || fileExtension.equals("JPG")
                || fileExtension.equals("JPEG") || fileExtension.equals("PNG")) {
            return RsData.of("S-6", "이미지 파일이 맞습니다.");
        } else {
            return RsData.of("F-6", "이미지만 업로드가 가능합니다.");
        }

    }

    // labelDetection + safeSearch
    public RsData<String> imageCheck(String imageUrl) throws IOException {

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(visionAPISettings);) {
            // 원격 저장소(NCP Object Storage)의 URL 사용하여 이미지 데이터를 읽어옴
            URL url = new URL(imageUrl);
            try (InputStream in = url.openStream()) {
                byte[] data = IOUtils.toByteArray(in);

                // URL을 통해 이미지를 빌드
                ByteString imgBytes = ByteString.copyFrom(data);
                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
                AnnotateImageRequest request =
                        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

                // 세이프 서치
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(
                        Collections.singletonList(request));
                List<AnnotateImageResponse> responseList = response.getResponsesList();

                for (AnnotateImageResponse res : responseList) {
                    if (res.hasError()) {
                        System.out.format("Error: %s%n", res.getError().getMessage());
                        return RsData.of("F-1", "오류가 발생하였습니다.");
                    }

                    SafeSearchAnnotation safeSearchAnnotation = res.getSafeSearchAnnotation();
                    List<EntityAnnotation> labelAnnotations = res.getLabelAnnotationsList();

                    // 세이프 서치 분석
                    List<String> safeSearchResultList = new ArrayList<>();
                    safeSearchResultList.add(safeSearchAnnotation.getAdult().toString());
                    safeSearchResultList.add(safeSearchAnnotation.getMedical().toString());
                    safeSearchResultList.add(safeSearchAnnotation.getAdult().toString());
                    safeSearchResultList.add(safeSearchAnnotation.getViolence().toString());
                    safeSearchResultList.add(safeSearchAnnotation.getRacy().toString());

                    for (String result : safeSearchResultList) {
                        if (result.equals("POSSIBLE")
                                || result.equals("LIKELY")
                                || result.equals("VERY_LIKELY")) {
                            return RsData.of("F-2", "유해할 확률이 높은 이미지입니다.");
                        }
                    }

                    // 라벨 검출
                    for (EntityAnnotation annotation : labelAnnotations) {
                        if (annotation.getDescription().contains("Food") && annotation.getScore() >= 0.7) {
                            return RsData.of("S-1", "음식 이미지일 확률이 높습니다.");
                        }
                    }
                }
            }
            return RsData.of("F-3", "음식 이미지가 아닐 확률이 높습니다. 다른 이미지를 사용해 주세요.");
        }
    }


    public String getPostDetailPhoto(String photoUrl) {

        String[] split = photoUrl.split("calocheck/");

        StringBuilder sb = new StringBuilder();
        sb.append(s3ConfigProperties.getCdnUrl());
        sb.append(split[1]);
        sb.append("?type=m&w=500&h=500&quality=90&bgcolor=FFFFFF&extopt=3");

        return sb.toString();
    }

    public List<String> getRecommendPhotoData(List<String> recommendList) {

        List<String> imgList = new ArrayList<>();

        for (String recommend : recommendList) {

            StringBuilder sb = new StringBuilder();

            sb.append(s3ConfigProperties.getCdnUrl());
            sb.append("recommendImg/");
            sb.append(recommend);
            sb.append(".jpg?type=m&w=200&h=200&quality=90&bgcolor=FFFFFF&ttype=jpg&extopt=3");

            imgList.add(sb.toString());
        }

        return imgList;
    }

}
