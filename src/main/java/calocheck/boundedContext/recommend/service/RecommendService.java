package calocheck.boundedContext.recommend.service;

import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.repository.RecommendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

       recommendRepository.save(recommend);
    }

    public List<Recommend> getAllRecommendList(){
        return recommendRepository.findAll();
    }

    @Transactional
    public Recommend getRecommendByName(String selectedValue){

        String nutritionName = formattingJsonToStr(selectedValue);

        Optional<Recommend> byNutritionName = recommendRepository.findByNutritionName(nutritionName);

        return byNutritionName.orElse(null);
    }

    public String formattingJsonToStr(String selectedValue){

        return selectedValue.substring(1, selectedValue.length() - 1);
    }


}
