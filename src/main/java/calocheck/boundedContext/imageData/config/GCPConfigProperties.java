package calocheck.boundedContext.imageData.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Setter
@Configuration
@ConfigurationProperties(prefix = "image.gcp.auth")
public class GCPConfigProperties {

    @Getter
    private String filePath;

}
