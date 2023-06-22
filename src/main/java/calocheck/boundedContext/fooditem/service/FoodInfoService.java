package calocheck.boundedContext.fooditem.service;

import calocheck.boundedContext.fooditem.entity.NutritionInfo;
import calocheck.boundedContext.fooditem.entity.FoodInfo;
import calocheck.boundedContext.fooditem.repository.FoodInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodInfoService {
    private final FoodInfoRepository foodInfoRepository;

    public FoodInfo create(NutritionInfo nutritionInfo,
                           String foodName, String manufacturer, String category,
                           int portionSize, String unit, int totalSize) {

        FoodInfo foodItem = FoodInfo.builder()
                .nutritionInfo(nutritionInfo)
                .foodName(foodName).manufacturer(manufacturer).category(category)
                .portionSize(portionSize).unit(unit).totalSize(totalSize)
                .build();

        return foodInfoRepository.save(foodItem);
    }

    public FoodInfo update(FoodInfo foodInfo, NutritionInfo nutritionInfo,
                           String foodName, String manufacturer, String category,
                           int portionSize, String unit, int totalSize) {

        FoodInfo updated = foodInfo.toBuilder()
                .nutritionInfo(nutritionInfo)
                .foodName(foodName).manufacturer(manufacturer).category(category)
                .portionSize(portionSize).unit(unit).totalSize(totalSize)
                .build();

        return foodInfoRepository.save(updated);
    }

    public void delete(FoodInfo foodInfo) {
        foodInfoRepository.delete(foodInfo);
    }

    public FoodInfo findById(Long id) {
        Optional<FoodInfo> foodInfo = foodInfoRepository.findById(id);

        return foodInfo.orElse(null);
    }
}
