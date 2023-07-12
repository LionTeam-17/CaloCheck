package calocheck.boundedContext.tag.config;

import calocheck.boundedContext.tag.service.TagService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class TagConfig {
    private final TagConfigProperties tagConfigProp;
    private final TagService tagService;

    @Bean
    public ApplicationRunner createTag() {
        return args -> {
            if(tagService.findAllTag().isEmpty()){
                List<String> settingList = tagConfigProp.getSettingList();
                List<String> nameList = tagConfigProp.getNameList();
                Map<String, Double> criteriaMap = tagConfigProp.getCriteria();
                Map<String, String> colorCodeMap = tagConfigProp.getColorCode();

                for(int i=0; i<settingList.size(); i++){

                    String settings = settingList.get(i);

                    tagService.createTag(nameList.get(i), colorCodeMap.get(settings),  criteriaMap.get(settings));
                }
            }
        };
    }
}
