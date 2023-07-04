package calocheck.boundedContext.mealHistory.service;

import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.service.CriteriaService;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.service.DailyMenuService;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.repository.MealHistoryRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.repository.NutrientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealHistoryService {
    private final MealHistoryRepository mealHistoryRepository;
    private final NutrientRepository nutrientRepository;
    private final CartFoodInfoService cartFoodInfoService;
    private final DailyMenuService dailyMenuService;

    public MealHistory create(Member member, List<DailyMenu> dailyMenuList, String mealType, String mealMemo, int mealScore) {

        List<CartFoodInfo> cartList = cartFoodInfoService.findAllByMember(member);
        List<Nutrient> nutrientTotal = cartFoodInfoService.calculateTotalNutrient(cartList);

        //fixme 여기서 repo를 참조하면 좋지 않음.
        nutrientRepository.saveAll(nutrientTotal);

        MealHistory mealHistory = MealHistory.builder()
                .member(member)
                .dailyMenuList(dailyMenuList)
                .mealType(mealType)
                .mealMemo(mealMemo)
                .mealScore(mealScore)
                .nutrients(nutrientTotal)
                .build();

        mealHistoryRepository.save(mealHistory);

        for (int i = 0; i < dailyMenuList.size(); i++) {
            DailyMenu buildMenu = dailyMenuList.get(i).toBuilder()
                    .mealHistory(mealHistory)
                    .build();

            dailyMenuService.updateMealHistory(buildMenu);
        }

        return mealHistory;
    }

    public void delete(MealHistory mealHistory) {
        mealHistoryRepository.delete(mealHistory);
    }

    public MealHistory findById(Long id) {
        Optional<MealHistory> mealHistory = mealHistoryRepository.findById(id);

        return mealHistory.orElse(null);
    }

    public MealHistory getByMember(Member member) {

        return mealHistoryRepository.findByMember(member).orElse(null);
    }

    //권장량 - total => (권장량 - 오늘 먹은 양) - 지금 먹을 양
    public List<Nutrient> calcNutrient(Criteria myCriteria, List<MealHistory> todayMealHistory, List<Nutrient> cartNutrientTotal) {

        //1. 권장량
        //2. 오늘 먹은 양
        //3. 지금 먹을 양
        //(권장량 - 오늘먹은 양) - 지금 먹을 양

        Map<String, Double> historyTotalMap = new HashMap<>();

        //FIXME Sample
        String[] nameList = {"단백질", "탄수화물", "지방", "식이섬유", "칼슘", "나트륨", "칼륨", "마그네슘"};

        Map<String, Integer> todayNutritionMap = calcTodayNutrition(todayMealHistory);

        List<Nutrient> calcList = Arrays.stream(nameList)
                .map(name -> cartNutrientTotal.stream()
                        .filter(nutrient -> nutrient.getName().equals(name))
                        .findFirst()
                        .map(nutrient -> Nutrient.builder()
                                .name(name)
                                .value(formattingDoubleValue(
                                        myCriteria.findByStrName(name) -
                                                todayNutritionMap.get(name) -
                                                nutrient.getValue())) // 권장량 - 오늘먹은 양 - 지금 먹는 양
                                .unit(nutrient.getUnit())
                                .build())
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return calcList;
    }

    public double formattingDoubleValue(double value) {

        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);

        return decimal.doubleValue();
    }

    public Map<String, Integer> calcTodayNutrition(List<MealHistory> todayMealHistory) {

        Map<String, Integer> calcNutritionMap = new HashMap<>();

        int calcProtein = 0;
        int calcFat = 0;
        int calcCarbohydrate = 0;
        int calcFiber = 0;
        int calcCalcium = 0;
        int calcMagnesium = 0;
        int calcPotassium = 0;
        int calcSodium = 0;

        if (todayMealHistory.isEmpty()) {
            System.out.println("오늘의 식단이 존재하지 않습니다!!");
        } else {

            for (int i = 0; i < todayMealHistory.size(); i++) {

                List<Nutrient> nutrients = todayMealHistory.get(i).getNutrients();

                for (Nutrient nutrient : nutrients) {

                    switch (nutrient.getName()) {
                        case ("단백질"):
                            calcProtein = (int) (calcProtein + nutrient.getValue());
                            break;
                        case ("지방"):
                            calcFat = (int) (calcFat + nutrient.getValue());
                            break;
                        case ("탄수화물"):
                            calcCarbohydrate = (int) (calcCarbohydrate + nutrient.getValue());
                            break;
                        case ("총식이섬유"):
                            calcFiber = (int) (calcFiber + nutrient.getValue());
                            break;
                        case ("칼슘"):
                            calcCalcium = (int) (calcCalcium + nutrient.getValue());
                            break;
                        case ("마그네슘"):
                            calcMagnesium = (int) (calcMagnesium + nutrient.getValue());
                            break;
                        case ("칼륨"):
                            calcPotassium = (int) (calcPotassium + nutrient.getValue());
                            break;
                        case ("나트륨"):
                            calcSodium = (int) (calcSodium + nutrient.getValue());
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

        return calcNutritionMap;
    }

    public List<MealHistory> findByMemberAndCreateDate(Member member) {

        return mealHistoryRepository.findByMemberAndCreateDate(member, LocalDate.now());
    }
}
