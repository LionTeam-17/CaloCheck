package calocheck.boundedContext.criteria.service;

import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.repository.CriteriaRepository;
import calocheck.boundedContext.mealHistory.service.MealHistoryService;
import calocheck.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CriteriaService {

    private final CriteriaRepository criteriaRepository;
    private final MealHistoryService mealHistoryService;

    @Transactional
    public Criteria create(String gender, int age, int protein,
                           int fiber, int calcium, int sodium,
                           int potassium, int magnesium, int carbohydrate, int fat) {


        Criteria criteria = Criteria.builder()
                .gender(gender)
                .age(age)
                .carbohydrate(carbohydrate)
                .protein(protein)
                .fat(fat)
                .fiber(fiber)
                .calcium(calcium)
                .sodium(sodium)
                .potassium(potassium)
                .magnesium(magnesium)
                .build();

        criteriaRepository.save(criteria);

        return criteria;
    }

    public Criteria findByGenderAndAge(Member member) {

        //fixme member에 gender 필요

        Criteria oCriteria;

        if (member.getAge() < 75) {
            oCriteria = criteriaRepository.findByGenderAndAge("남자", member.getAge()).orElse(null);
        } else {
            oCriteria = criteriaRepository.findByGenderAndAge("남자", 75).orElse(null);
        }

        return oCriteria;
    }

    public double calcMemberKcal(String gender, Integer age, Double height, Double weight) {

        double calcKcal = 0;

        //해리스-베네딕트 계산
        if (gender.equals("남자")) {
            calcKcal = 88.4 + (13.4 * weight) + (4.8 * height) - (5.68 * age);
        } else {
            calcKcal = 447.6 + (9.25 * weight) + (3.1 * height) - (4.33 * age);
        }

        return formattingDoubleValue(calcKcal);
    }

    public double formattingDoubleValue(double value) {

        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);

        return decimal.doubleValue();
    }

}
