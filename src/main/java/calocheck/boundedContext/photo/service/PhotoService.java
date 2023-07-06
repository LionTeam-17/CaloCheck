package calocheck.boundedContext.photo.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.photo.config.GCPConfigProperties;
import calocheck.boundedContext.photo.config.ObjectStorageConfigProperties;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import jakarta.servlet.http.HttpServletRequest;
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

    private final GCPConfigProperties gcpConfigProperties;
    private final ObjectStorageConfigProperties objectStorageConfigProperties;

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

        String bucketName = objectStorageConfigProperties.getS3().get("bucket");
        String endPoint = objectStorageConfigProperties.getS3().get("endpoint");

        PutObjectResult putObjectResult = objectStorageConfigProperties.amazonS3Client()
                .putObject(new PutObjectRequest(bucketName, key, inputStream, objectMetadata)
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

    //이미지의 라벨 검출
    public RsData<String> detectLabelsRemote(String imageUrl) throws IOException {
        try (ImageAnnotatorClient vision = gcpConfigProperties){

            // 원격 저장소(NCP Object Storage)의 URL 사용하여 이미지 데이터를 읽어옴
            URL url = new URL(imageUrl);
            try (InputStream in = url.openStream()) {
                byte[] data = IOUtils.toByteArray(in);

                //URL 을 통해 이미지를 빌드
                ByteString imgBytes = ByteString.copyFrom(data);
                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
                AnnotateImageRequest request =
                        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

                // Label 검출
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(
                        Collections.singletonList(request));
                List<AnnotateImageResponse> responses = response.getResponsesList();

                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        System.out.format("Error: %s%n", res.getError().getMessage());
                        return RsData.of("F-1", "라벨 검출도중 오류가 발생하였습니다.");
                    }

                    for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
//                        annotation.getAllFields().forEach((k, v) ->
//                                System.out.format("%s : %s%n", k, v.toString()));

                        //검출 결과 중에서 음식일 확률이 70% 이상이라면 바로 리턴 후 종료 -> true 반환
                        if (annotation.getDescription().contains("Food") && annotation.getScore() >= 0.7) {
                            return RsData.of("S-1", "음식 이미지일 확률이 높습니다.");
                        }
                    }
                }
            }
        }
        return RsData.of("F-1", "음식 이미지가 아닙니다.");
    }

    //이미지 유해성 검사
    public RsData<String> detectSafeSearchRemote(String imageUrl) throws IOException {

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // 원격 저장소(NCP Object Storage)의 URL 사용하여 이미지 데이터를 읽어옴
            URL url = new URL(imageUrl);
            try (InputStream in = url.openStream()) {
                byte[] data = IOUtils.toByteArray(in);

                //URL 을 통해 이미지를 빌드
                ByteString imgBytes = ByteString.copyFrom(data);
                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build();
                AnnotateImageRequest request =
                        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

                // 세이프 서치
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(
                        Collections.singletonList(request));
                List<AnnotateImageResponse> responses = response.getResponsesList();

                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        System.out.format("Error: %s%n", res.getError().getMessage());
                        return RsData.of("F-1", "세이프서치 도중 오류가 발생하였습니다.");
                    }

                    SafeSearchAnnotation safeSearchAnnotation = res.getSafeSearchAnnotation();

                    //유해성 등급 => Unknown, Very Unlikely, Unlikely, Possible, Likely, and Very Likely
                    List<String> resultList = new ArrayList<>();

                    resultList.add(safeSearchAnnotation.getAdult().toString());
                    resultList.add(safeSearchAnnotation.getMedical().toString());
                    resultList.add(safeSearchAnnotation.getAdult().toString());
                    resultList.add(safeSearchAnnotation.getViolence().toString());
                    resultList.add(safeSearchAnnotation.getRacy().toString());

                    for (String result : resultList) {

                        if (result.equals("POSSIBLE")
                                || result.equals("LIKELY")
                                || result.equals("VERY_LIKELY")) {

                            return RsData.of("F-2", "유해할 확률이 높은 이미지 입니다.");
                        }
                    }
                }
            }
        }
        return RsData.of("S-1", "유해성 검사를 통과한 이미지 입니다.");
    }

    public String getPostDetailPhoto(String photoUrl){

        String[] split = photoUrl.split("calocheck/");

        StringBuilder sb = new StringBuilder();
        sb.append("https://mxpijmgqueja17962851.cdn.ntruss.com/");
        sb.append(split[1]);
        sb.append("?type=m&w=500&h=500&quality=90&bgcolor=FFFFFF&extopt=3");

        return sb.toString();
    }

    public List<String> getRecommendPhotoData(String[] recommendList){

        String protocol = request.getScheme();

        List<String> imgList = new ArrayList<>();

        for (String s : recommendList) {

            StringBuilder sb = new StringBuilder();

            if (protocol.equals("http")) {
                sb.append("http://mxpijmgqueja17962851.cdn.ntruss.com/recommendImg/");
            } else if (protocol.equals("https")) {
                sb.append("https://mxpijmgqueja17962851.cdn.ntruss.com/recommendImg/");
            }

            sb.append(s);
            sb.append(".jpg?type=m&w=200&h=200&quality=90&bgcolor=FFFFFF&ttype=jpg&extopt=3");

            imgList.add(sb.toString());
        }

        return imgList;
    }

}
