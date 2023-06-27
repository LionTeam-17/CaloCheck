package calocheck.boundedContext.recommend.service;

import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.repository.RecommendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;

    @Transactional
    public void createRecommend(
            String nutritionName, String description, String[] foodList
    ) {

        Recommend recommend = Recommend.builder()
                .nutritionName(nutritionName)
                .description(description)
                .foodList(foodList)
                .build();

        Recommend save = recommendRepository.save(recommend);
    }

    @Transactional
    public Recommend getRecommendByName(String selectedValue){

        Optional<Recommend> byNutritionName = recommendRepository.findByNutritionName(selectedValue);

        return byNutritionName.orElse(null);
    }


}
