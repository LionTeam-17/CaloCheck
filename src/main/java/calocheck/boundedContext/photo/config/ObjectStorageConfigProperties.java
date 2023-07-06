package calocheck.boundedContext.photo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "image.aws")
public class ObjectStorageConfigProperties {

    private String accessKey;

    private String secretKey;

    private String region;

    private String endPoint;

    private String bucket;

    private String sampleImg;

    private String cdnUrl;

}
