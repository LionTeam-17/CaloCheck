package calocheck.boundedContext.photo.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "image.optimizer")
public class OptimizerConfigProperties {

    private String recommendOptimizer;


}
