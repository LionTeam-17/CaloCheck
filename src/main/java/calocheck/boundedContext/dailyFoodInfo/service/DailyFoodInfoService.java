package calocheck.boundedContext.dailyFoodInfo.service;

import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.dailyFoodInfo.entity.DailyFoodInfo;
import calocheck.boundedContext.dailyFoodInfo.repository.DailyFoodInfoRepository;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.service.NutrientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyFoodInfoService {

    private final DailyFoodInfoRepository dailyFoodInfoRepository;
    private final CartFoodInfoService cartFoodInfoService;
    private final NutrientService nutrientService;

    public DailyFoodInfo create(Member member, FoodInfo foodInfo, String mealTime, double quantity) {
        DailyFoodInfo dailyFoodInfo = DailyFoodInfo.builder()
                .member(member)
                .foodInfo(foodInfo)
                .mealTime(mealTime)
                .quantity(quantity)
                .build();

        return dailyFoodInfoRepository.save(dailyFoodInfo);
    }

//    //식단 수정? => 가능한가? 어디에서?
//    public DailyFoodInfo udpate(DailyFoodInfo dailyFoodInfo) {
//
//        DailyFoodInfo updated = dailyFoodInfo.toBuilder()
//                .mealTime(meal)
//                .build();
//
//        return dailyFoodInfoRepository.save(updated);
//    }

    public void delete(DailyFoodInfo dailyFoodInfo) {
        dailyFoodInfoRepository.delete(dailyFoodInfo);
    }

    public DailyFoodInfo findById(Long id) {
        Optional<DailyFoodInfo> dailyFoodInfo = dailyFoodInfoRepository.findById(id);

        return dailyFoodInfo.orElse(null);
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
