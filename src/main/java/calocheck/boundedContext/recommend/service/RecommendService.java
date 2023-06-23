package calocheck.boundedContext.recommend.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.fooditem.entity.FoodInfo;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.recommend.config.RecommendConfig;
import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.repository.RecommendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;

    @Transactional
    public RsData<Recommend> createRecommend(
            String nutritionName, String description, String[] foodList
    ) {

        Recommend recommend = Recommend.builder()
                .nutritionName(nutritionName)
                .description(description)
                .foodList(foodList)
                .build();

        Recommend save = recommendRepository.save(recommend);

        System.out.println("save.getNutritionName() = " + save.getNutritionName());
        System.out.println("save.getDescription() = " + save.getDescription());
        System.out.println("Arrays.toString(save.getFoodList()) = " + Arrays.toString(save.getFoodList()));
        
        
        return RsData.of("S-1", "%s 에 대한 추천 내용이 생성되었습니다.".formatted(nutritionName));
    }


}
