package calocheck.boundedContext.photo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Configuration
@ConfigurationProperties(prefix = "custom.aws")
public class ObjectStorageConfigProperties {

    private Map<String, String> credentials;

    private Map<String, String> stack;

    private Map<String, String> region;

    @Getter
    private Map<String, String> s3;

    private Map<String, String> cdn;

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(credentials.get("access-key"),credentials.get("secret-key"));
        return (AmazonS3) AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3.get("endpoint"), region.get("static")))
                .build();
    }

}
