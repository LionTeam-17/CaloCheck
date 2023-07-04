package calocheck.boundedContext.criteria.service;

import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.repository.CriteriaRepository;
import calocheck.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CriteriaService {

    private final CriteriaRepository criteriaRepository;

    @Transactional
    public Criteria create(String gender, int age, int protein,
                           int fiber, int calcium, int sodium,
                           int potassium, int magnesium){

        Criteria criteria = Criteria.builder()
                .gender(gender)
                .age(age)
                .kcal(2000) // fixme 사람마다 계산해야? 그럼 여기 있으면 안될지도
                .carbohydrate(300) //fixme 탄수화물 보류
                .protein(protein)
                .fat(30) //fixme 지방 보류
                .fiber(fiber)
                .calcium(calcium)
                .sodium(sodium)
                .potassium(potassium)
                .magnesium(magnesium)
                .build();

        criteriaRepository.save(criteria);

        return criteria;
    }

    public Criteria findByGenderAndAge(Member member){

        //fixme member에 gender 필요

        Criteria oCriteria;

        if(member.getAge()<75){
            oCriteria = criteriaRepository.findByGenderAndAge("남자", member.getAge()).orElse(null);
        } else{
            oCriteria = criteriaRepository.findByGenderAndAge("남자", 75).orElse(null);
        }

        return oCriteria;

    }


}