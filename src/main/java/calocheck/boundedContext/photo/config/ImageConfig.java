package calocheck.boundedContext.photo.config;

import calocheck.base.rsData.RsData;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ImageConfig {

    private final ObjectStorageConfigProperties osConfigProp;
    private final GCPConfigProperties gcpConfigProp;

    @Bean
    public ImageAnnotatorClient imageVision() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(gcpConfigProp.getFilePath()));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(() -> googleCredentials).build();
        return ImageAnnotatorClient.create(settings);
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(osConfigProp.getAccessKey(), osConfigProp.getSecretKey());
        return (AmazonS3) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(osConfigProp.getEndPoint(), osConfigProp.getRegion()))
                .build();
    }

}
