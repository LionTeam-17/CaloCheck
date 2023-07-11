package calocheck.boundedContext.imageData.config;

import calocheck.base.util.s3.config.S3Config;
import calocheck.base.util.s3.service.S3Service;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class ImageConfig {

    private final S3Config s3ConfigProperties;
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
