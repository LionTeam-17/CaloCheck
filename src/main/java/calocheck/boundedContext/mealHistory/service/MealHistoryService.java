package calocheck.boundedContext.mealHistory.service;

import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.service.DailyMenuService;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.repository.MealHistoryRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealHistoryService {
    private final MealHistoryRepository mealHistoryRepository;
    private final DailyMenuService dailyMenuService;

    public MealHistory create(Member member, List<DailyMenu> dailyMenuList, String mealType, String mealMemo, int mealScore) {

        MealHistory mealHistory = MealHistory.builder()
                .member(member)
                .dailyMenuList(dailyMenuList)
                .mealType(mealType)
                .mealMemo(mealMemo)
                .mealScore(mealScore)
                .build();

        mealHistoryRepository.save(mealHistory);

        return mealHistory;
    }

    public void delete(MealHistory mealHistory) {
        mealHistoryRepository.delete(mealHistory);
    }

    public MealHistory findById(Long id) {
        Optional<MealHistory> mealHistory = mealHistoryRepository.findById(id);

        return mealHistory.orElse(null);
    }

    public MealHistory getByMember(Member member){

        return mealHistoryRepository.findByMember(member).orElse(null);
    }

    //권장량 - total => (권장량 - 오늘 먹은 양) - 지금 먹을 양
    public List<Nutrient> calcNutrient(Member member, List<Nutrient> cartNutrientTotal) {

        //1. 권장량
        //2. 오늘 먹은 양
        //3. 지금 먹을 양
        //(권장량 - 오늘먹은 양) - 지금 먹을 양

        //FIXME Sample
        String[] nutrientName = {"단백질", "탄수화물", "지방"};

        List<Nutrient> calcList = Arrays.stream(nutrientName)
                .map(name -> cartNutrientTotal.stream()
                        .filter(nutrient -> nutrient.getName().equals(name))
                        .findFirst()
                        .map(nutrient -> Nutrient.builder()
                                .name(name)
                                .value(formattingDoubleValue(nutrient.getValue() - 20 - 10)) // FIXME (권장량 - 오늘먹은 양) - 지금 먹을 양
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
}
