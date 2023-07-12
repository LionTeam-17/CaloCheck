package calocheck.boundedContext.imageData.service;

import calocheck.base.rsData.RsData;
import calocheck.base.util.s3.config.S3Config;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.imageData.config.OptimizerConfigProperties;
import calocheck.boundedContext.imageData.entity.ImageData;
import calocheck.boundedContext.imageData.imageTarget.ImageTarget;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@Transactional
class ImageDataServiceTest {

    @Autowired
    private ImageDataService imageDataService;
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private OptimizerConfigProperties optimizerConfigProp;
    @Autowired
    private S3Config s3Config;

    private MultipartFile nullFile;
    private MultipartFile imgFile;
    private MultipartFile txtFile;
    private Member user1;
    private Post testPost;
    private FoodInfo testFood;

    @BeforeEach
    void setUp() {

        if(memberService.findByUsername("user1").isPresent()){
            user1 = memberService.findByUsername("user1").get();
        }

        testFood = FoodInfo.builder()
                .foodCode("test")
                .foodName("testFood")
                .build();

        testPost = postService.savePost("테스트 포스트 제목", "테스트 포스트 내용", "A", user1).getData();

        nullFile = new MockMultipartFile(
                "empty.txt",
                new byte[0]
        );

        Path imgFilePath = Paths.get("src/main/resources/static/img/tmp_logo.png");
        try {
            byte[] fileBytes = Files.readAllBytes(imgFilePath);
            imgFile = new MockMultipartFile(
                    "sample.png",
                    "sample.png",
                    "image/png",
                    fileBytes
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

        Path txtFilePath = Paths.get("src/main/resources/static/img/sample.txt");
        try {
            byte[] fileBytes = Files.readAllBytes(txtFilePath);
            txtFile = new MockMultipartFile(
                    "sample.txt",
                    "sample.txt",
                    "text/plain",
                    fileBytes
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("파일이 없어도 작성이 가능하지만, 파일이 있다면 그 파일은 반드시 이미지 파일이어야 한다.")
    void t001() {

        RsData<ImageData> nullRsData = imageDataService.isImgFile(nullFile.getOriginalFilename());
        RsData<ImageData> imgRsData = imageDataService.isImgFile(imgFile.getOriginalFilename());
        RsData<ImageData> txtRsData = imageDataService.isImgFile(txtFile.getOriginalFilename());

        assertThat("S-7".equals(nullRsData.getResultCode())).isTrue();      //파일 없어도 작성 가능
        assertThat("S-6".equals(imgRsData.getResultCode())).isTrue();       //이미지 파일이므로 작성 가능
        assertThat("F-6".equals(txtRsData.getResultCode())).isTrue();       //파일이 이미지 파일이 아니므로 작성 불가능
    }

    @Test
    @DisplayName("이미지 업로드시 ImageTarget에 따라 각각 지정된 s3의 다른 디렉토리에 저장된다.")
    void t002() throws IOException {

        String foodImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.FOOD_IMAGE);
        String postImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.POST_IMAGE);

        assertThat(foodImgUrl.contains(s3Config.getBucket())).isTrue();
        assertThat(foodImgUrl.contains(s3Config.getEndPoint())).isTrue();
        assertThat(postImgUrl.contains(s3Config.getBucket())).isTrue();
        assertThat(postImgUrl.contains(s3Config.getEndPoint())).isTrue();

        assertThat(foodImgUrl.contains("foodImage")).isTrue();
        assertThat(foodImgUrl.contains("post")).isFalse();
        assertThat(postImgUrl.contains("post")).isTrue();
        assertThat(postImgUrl.contains("foodImage")).isFalse();
    }

    @Test
    @DisplayName("이미지 업로드가 성공적으로 수행되고, 해당 경로로 이미지 다운로드 및 추가작업이 가능하다")
    void t003() throws IOException {

        String foodImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.FOOD_IMAGE);
        String postImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.POST_IMAGE);

        File foodImageFile = downloadImageFile(foodImgUrl);
        File postImageFile = downloadImageFile(postImgUrl);

        assertThat(foodImageFile).exists();
        assertThat(postImageFile).exists();

    }

    private File downloadImageFile(String imageUrl) throws IOException {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        Path tempFilePath = Files.createTempFile("temp", fileName);
        Resource resource = new UrlResource(imageUrl);
        Files.copy(resource.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
        return tempFilePath.toFile();
    }

    @Test
    @DisplayName("어떤 내용에 대한 이미지인지 ImageData 객체를 생성하며, 하나의 타겟당 하나의 이미지만 저장 가능")
    void t004() throws IOException {

        String foodImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.FOOD_IMAGE);

        //testFood 에 대한 FOOD_IMAGE 등록
        RsData<ImageData> createRsData1 =
                imageDataService.createImageData(ImageTarget.FOOD_IMAGE, foodImgUrl, testFood.getId());

        //하나의 음식에 두개의 이미지는 저장 불가
        RsData<ImageData> createRsData2 =
                imageDataService.createImageData(ImageTarget.FOOD_IMAGE, foodImgUrl, testFood.getId());

        assertThat("S-1".equals(createRsData1.getResultCode())).isTrue();
        assertThat("F-1".equals(createRsData2.getResultCode())).isTrue();

        Optional<ImageData> oTestFoodImageData =
                imageDataService.findByImageTargetAndTargetId(ImageTarget.FOOD_IMAGE, testFood.getId());

        Optional<ImageData> emptyImageData =
                imageDataService.findByImageTargetAndTargetId(ImageTarget.FOOD_IMAGE, 2L);

        assertThat(oTestFoodImageData.isPresent()).isTrue();
        assertThat(emptyImageData.isPresent()).isFalse();
    }

    @Test
    @DisplayName("이미지 세이프 서치 수행 가능")
    void t005() throws IOException {

        //Sample imgFile 은 유해하지 않은 이미지임.
        String foodImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.FOOD_IMAGE);

        RsData<ImageData> safeSearchRsData = imageDataService.detectSafeSearchRemote(foodImgUrl);

        assertThat("S-1".equals(safeSearchRsData.getResultCode())).isTrue();
    }

    @Test
    @DisplayName("이미지 label 검출 수행 가능")
    void t006() throws IOException {

        //Sample imgFile 은 음식 이미지가 아님.
        String foodImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.FOOD_IMAGE);

        RsData<ImageData> detectLabelsRsData = imageDataService.detectLabelsRemote(foodImgUrl);

        assertThat("F-1".equals(detectLabelsRsData.getResultCode())).isTrue();
        assertThat("음식 이미지가 아닐 확률이 높습니다.".equals(detectLabelsRsData.getMsg())).isTrue();
    }

    @Test
    @DisplayName("이미지 종류에 따라 다른 이미지 프로세싱 수행 가능")
    void t007() throws IOException {

        String foodImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.FOOD_IMAGE);
        ImageData foodImageData =
                imageDataService.createImageData(ImageTarget.FOOD_IMAGE, foodImgUrl, testFood.getId()).getData();

        String postImgUrl = imageDataService.imageUpload(imgFile, ImageTarget.POST_IMAGE);
        ImageData postImageData =
                imageDataService.createImageData(ImageTarget.POST_IMAGE, postImgUrl, testPost.getId()).getData();

        String foodProcessingRs = imageDataService.imageProcessing(foodImageData);
        String postProcessingRs = imageDataService.imageProcessing(postImageData);

        assertThat(foodProcessingRs.contains(optimizerConfigProp.getFoodProcessing())).isTrue();
        assertThat(postProcessingRs.contains(optimizerConfigProp.getPostProcessing())).isTrue();
    }


}