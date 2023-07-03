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

    public double calcMemberKcal(Member member) {

//        String gender = member.getGender();       fixme
        String gender = "남자";
        Integer age = member.getAge();
        Double height = member.getHeight();
        Double weight = member.getWeight();

        double calcKcal = 0;

        //해리스-베네딕트 계산
        if (gender.equals("남자")) {
            calcKcal = 88.4 + (13.4 * weight) + (4.8 * height) - (5.68 * age);
        } else {
            calcKcal = 447.6 + (9.25 * weight) + (3.1 * height) - (4.33 * age);
        }

        return calcKcal;
    }


}
