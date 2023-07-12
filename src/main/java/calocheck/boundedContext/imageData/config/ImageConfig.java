package calocheck.boundedContext.imageData.config;

import calocheck.base.util.s3.service.S3Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class ImageConfig {

    private final GCPConfigProperties gcpConfigProperties;
    private final S3Service s3Service;

    @Bean
    public ImageAnnotatorSettings visionAPISettings() throws IOException {
        InputStream inputStream = s3Service.getFileFromS3(gcpConfigProperties.getFilePath());
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream);
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(() -> googleCredentials).build();

        return settings;
    }
}
