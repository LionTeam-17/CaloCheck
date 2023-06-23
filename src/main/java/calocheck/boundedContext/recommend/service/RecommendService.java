package calocheck.boundedContext.recommend.service;

import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.repository.RecommendRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Recommend> getAllRecommendList(){
        return recommendRepository.findAll();
    }


}
