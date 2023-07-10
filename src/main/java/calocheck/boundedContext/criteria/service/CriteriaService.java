package calocheck.boundedContext.criteria.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.repository.CriteriaRepository;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.dto.NutrientDTO;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.tag.config.TagConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CriteriaService {

    private final CriteriaRepository criteriaRepository;
    private final TagConfigProperties tagConfigProp;

    @Transactional
    public Criteria create(String gender, int age, double protein,
                           double fiber, double calcium, double sodium,
                           double potassium, double magnesium, double carbohydrate, double fat) {

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

        //TODO member.gender ("male, female" => "남자, 여자") 엑셀파일 수정?

        Criteria oCriteria;

        if (member.getAge() < 75) {
            oCriteria = criteriaRepository.findByGenderAndAge(member.getGender(), member.getAge()).orElse(null);
        } else {
            oCriteria = criteriaRepository.findByGenderAndAge(member.getGender(), 75).orElse(null);
        }

        return oCriteria;
    }

    public double calcMemberKcal(String gender, Integer age, Double height, Double weight) {

        double calcKcal = 0;

        //해리스-베네딕트 계산
        if (gender.equals("M")) {
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

    //(권장량 - 오늘먹은 양) - 지금 먹을 양
    public List<NutrientDTO> calcNutrient(Criteria myCriteria, List<DailyMenu> todayMenuList, List<NutrientDTO> cartTotalNutrient) {
        List<String> nutrientList = tagConfigProp.getNameList();
        Map<String, Double> todayNutritionMap  = calcTodayNutrition(todayMenuList).getData();

        return nutrientList.stream()
                .map(name ->
                        cartTotalNutrient.stream()
                                .filter(nutrient -> nutrient.getName().equals(name))
                                .map(nutrient -> new NutrientDTO(name, myCriteria.getCriteria(name) - todayNutritionMap.get(name) - nutrient.getValue()))
                                .findFirst().orElse(null)
                )
                .collect(Collectors.toList());
    }

    public RsData<Map<String, Double>> calcTodayNutrition(List<DailyMenu> todayMenuList) {

        Map<String, Double> calcNutritionMap = new HashMap<>();

        double calcProtein = 0;
        double calcFat = 0;
        double calcCarbohydrate = 0;
        double calcFiber = 0;
        double calcCalcium = 0;
        double calcMagnesium = 0;
        double calcPotassium = 0;
        double calcSodium = 0;

        if(!todayMenuList.isEmpty()){

            for (DailyMenu dailyMenu : todayMenuList) {

                List<Nutrient> menuNutrients = dailyMenu.getFoodInfo().getNutrientList();

                for (Nutrient nutrient : menuNutrients) {

                    //FIXME Nutrient.getName에 g 이 포함된 상황
                    switch (nutrient.getName()) {
                        case ("단백질"):
                            calcProtein = (calcProtein + nutrient.getValue());
                            break;
                        case ("지방"):
                            calcFat = (calcFat + nutrient.getValue());
                            break;
                        case ("탄수화물"):
                            calcCarbohydrate = (calcCarbohydrate + nutrient.getValue());
                            break;
                        case ("총식이섬유"):
                            calcFiber = (calcFiber + nutrient.getValue());
                            break;
                        case ("칼슘"):
                            calcCalcium = (calcCalcium + nutrient.getValue());
                            break;
                        case ("마그네슘"):
                            calcMagnesium = (calcMagnesium + nutrient.getValue());
                            break;
                        case ("칼륨"):
                            calcPotassium = (calcPotassium + nutrient.getValue());
                            break;
                        case ("나트륨"):
                            calcSodium = (calcSodium + nutrient.getValue());
                            break;
                    }
                }
            }
        }

        calcNutritionMap.put("탄수화물", calcCarbohydrate);
        calcNutritionMap.put("단백질", calcProtein);
        calcNutritionMap.put("지방", calcFat);
        calcNutritionMap.put("식이섬유", calcFiber);
        calcNutritionMap.put("칼슘", calcCalcium);
        calcNutritionMap.put("마그네슘", calcMagnesium);
        calcNutritionMap.put("칼륨", calcPotassium);
        calcNutritionMap.put("나트륨", calcSodium);

        if (todayMenuList.isEmpty()) {
            return RsData.of("F-1", "오늘의 식단이 존재하지 않습니다", calcNutritionMap);
        }

        return RsData.of("S-1", "오늘의 영양소 계산이 완료되었습니다", calcNutritionMap);
    }

}
