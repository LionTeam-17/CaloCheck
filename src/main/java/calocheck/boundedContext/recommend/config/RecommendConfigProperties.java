package calocheck.boundedContext.recommend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "recommend")
public class RecommendConfigProperties {

    private List<String> recommendList;
    private List<String> settingList;
    private Map<String, String> description;
    private Map<String, List<String>> foodList;

}
