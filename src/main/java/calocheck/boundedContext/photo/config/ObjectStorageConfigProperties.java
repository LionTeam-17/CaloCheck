package calocheck.boundedContext.photo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cloud.aws")
public class ObjectStorageConfigProperties {

    private Map<String, String> credentials;

    private Map<String, String> stack;

    private Map<String, String> region;

    private Map<String, String> s3;

    private Map<String, String> cdn;

}
