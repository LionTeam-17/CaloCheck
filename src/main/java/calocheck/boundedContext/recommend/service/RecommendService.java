package calocheck.boundedContext.recommend.service;

import calocheck.base.rsData.RsData;
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
    public RsData<Recommend> createRecommend(
            String nutritionName, String description, List<String> foodList
    ) {

        Recommend recommend = Recommend.builder()
                .nutritionName(nutritionName)
                .description(description)
                .foodList(foodList)
                .build();

       recommendRepository.save(recommend);

       return RsData.of("S-1", "추천 데이터 생성 성공", recommend);
    }

    public List<Recommend> getAllRecommendList(){
        return recommendRepository.findAll();
    }

    @Transactional
    public Recommend getRecommendByName(String selectedValue){

        Optional<Recommend> byNutritionName = recommendRepository.findByNutritionName(selectedValue);

        return byNutritionName.orElse(null);
    }


}
