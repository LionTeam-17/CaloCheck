package calocheck.boundedContext.recommend.config;

import calocheck.boundedContext.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RecommendConfig {

    private final RecommendService recommendService;
    private final RecommendConfigProperties recommendConfigProp;

    @Bean
    public void createRecommend() {

        List<String> recommendList = recommendConfigProp.getRecommendList();
        List<String> settingList = recommendConfigProp.getSettingList();
        Map<String, String> description = recommendConfigProp.getDescription();
        Map<String, List<String>> foodList = recommendConfigProp.getFoodList();

//        for (int i = 0; i < settingList.size(); i++) {
//
//            recommendService.createRecommend(recommendList.get(i)
//                    , description.get(settingList.get(i))
//                    , foodList.get(settingList.get(i)));
//
//        }
    }

}
