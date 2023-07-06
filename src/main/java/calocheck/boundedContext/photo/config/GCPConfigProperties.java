package calocheck.boundedContext.photo.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Setter
@Configuration
@ConfigurationProperties(prefix = "cloud.gcp.auth")
public class GCPConfigProperties {

    private String filePath;

    @Bean
    public ImageAnnotatorClient imageVision() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream(filePath));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(() -> googleCredentials).build();
        return ImageAnnotatorClient.create(settings);
    }

}
