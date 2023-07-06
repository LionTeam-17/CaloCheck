package calocheck.boundedContext.photo.config;

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

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class ImageConfig {

    private final S3ConfigProperties s3ConfigProperties;
    private final GCPConfigProperties gcpConfigProperties;

    @Bean
    public ImageAnnotatorSettings visionAPISettings() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(gcpConfigProperties.getFilePath()));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(() -> googleCredentials).build();

        return settings;
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(s3ConfigProperties.getAccessKey(), s3ConfigProperties.getSecretKey());
        return (AmazonS3) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3ConfigProperties.getEndPoint(), s3ConfigProperties.getRegion()))
                .build();
    }

}
