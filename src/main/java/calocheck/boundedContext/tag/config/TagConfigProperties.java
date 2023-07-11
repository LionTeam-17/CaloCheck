package calocheck.boundedContext.tag.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "custom.tag")
public class TagConfigProperties {

    private Map<String, Double> criteria;
    private Map<String, String> colorCode;
    private List<String> settingList;
    private List<String> nameList;

}
