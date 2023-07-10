package calocheck.boundedContext.imageData.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "image.optimizer")
public class OptimizerConfigProperties {

    private String postProcessing;

    private String foodProcessing;

    private String recommendProcessing;

}
