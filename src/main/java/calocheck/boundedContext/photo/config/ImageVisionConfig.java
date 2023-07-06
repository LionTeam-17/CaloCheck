package calocheck.boundedContext.photo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class ImageVisionConfig {

    @Value("${cloud.gcp.auth.filePath}")
    private String filePath;

    @Bean
    public ImageAnnotatorClient imageVision() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(filePath));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(() -> googleCredentials).build();
        return ImageAnnotatorClient.create(settings);
    }
}
