package calocheck.boundedContext.mealHistory.service;

import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.repository.MealHistoryRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.dto.NutrientDTO;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealHistoryService {
    private final MealHistoryRepository mealHistoryRepository;

    public MealHistory create(Member member, List<DailyMenu> dailyMenuList, String mealType, String mealMemo, int mealScore) {
        MealHistory mealHistory = MealHistory.builder()
                .member(member)
                .dailyMenuList(dailyMenuList)
                .mealType(mealType)
                .mealMemo(mealMemo)
                .mealScore(mealScore)
                .build();

        return mealHistoryRepository.save(mealHistory);
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

    //(권장량 - 오늘먹은 양) - 지금 먹을 양
    public List<NutrientDTO> calcNutrient(Criteria myCriteria, List<MealHistory> todayMealHistory, List<NutrientDTO> cartTotalNutrient) {
        String[] nameList = {"단백질", "탄수화물", "지방", "칼슘", "나트륨", "칼륨", "마그네슘"};
        Map<String, Integer> todayNutritionMap = calcTodayNutrition(todayMealHistory);

        return Arrays.stream(nameList)
                .map(name ->
                    cartTotalNutrient.stream()
                            .filter(nutrient -> nutrient.getName().equals(name))
                            .map(nutrient -> new NutrientDTO(name, myCriteria.getCriteria(name) - todayNutritionMap.get(name) - nutrient.getValue()))
                            .findFirst().orElse(null)
                )
                .collect(Collectors.toList());
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
                List<DailyMenu> dailyMenuList = todayMealHistory.get(i).getDailyMenuList();
                for (int j=0; j<dailyMenuList.size(); j++) {
                    DailyMenu dailyMenu = dailyMenuList.get(j);
                    List<Nutrient> nutrients = dailyMenu.getFoodInfo().getNutrientList();

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
        LocalDateTime startDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return mealHistoryRepository.findByMemberAndCreateDateBetween(member, startDateTime, endDateTime);
    }

    public List<MealHistory> findByMemberAndDate(Member member, LocalDate date) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(23, 59, 59);
        return mealHistoryRepository.findByMemberAndCreateDateBetween(member, startDateTime, endDateTime);
    }

}
