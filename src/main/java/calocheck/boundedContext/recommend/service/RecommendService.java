package calocheck.boundedContext.recommend.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.fooditem.entity.FoodInfo;
import calocheck.boundedContext.member.entity.Member;
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
    public RsData<Recommend> createRecommend(
            String nutritionName, String description, List<FoodInfo> foodList
    ) {

        Recommend recommend = Recommend.builder()
                .nutritionName(nutritionName)
                .description(description)
                .foodList(foodList)
                .build();

        recommendRepository.save(recommend);
        return RsData.of("S-1", "%s 에 대한 추천 내용이 생성되었습니다.".formatted(nutritionName));
    }


}
