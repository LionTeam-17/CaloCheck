package calocheck.boundedContext.photo.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "sample")
public class GCPConfigProperties {

    @Getter
    @Setter
    private String example;

    @Getter
    @Setter
    private String example2;

}
