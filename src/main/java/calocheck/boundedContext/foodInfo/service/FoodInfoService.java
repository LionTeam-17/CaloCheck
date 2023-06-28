package calocheck.boundedContext.foodInfo.service;

import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.repository.FoodInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodInfoService {
    private final FoodInfoRepository foodInfoRepository;

    public FoodInfo create(NutrientInfo nutrientInfo,
                           String foodName, String manufacturer, String category,
                           int portionSize, String unit, int totalSize) {

        FoodInfo foodItem = FoodInfo.builder()
                .nutrientInfo(nutrientInfo)
                .foodName(foodName).manufacturer(manufacturer).category(category)
                .portionSize(portionSize).unit(unit).totalSize(totalSize)
                .build();

        return foodInfoRepository.save(foodItem);
    }

    public FoodInfo update(FoodInfo foodInfo, NutrientInfo nutrientInfo,
                           String foodName, String manufacturer, String category,
                           int portionSize, String unit, int totalSize) {

        FoodInfo updated = foodInfo.toBuilder()
                .nutrientInfo(nutrientInfo)
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

    public List<FoodInfo> findAll() {
        return foodInfoRepository.findAll();
    }

    public Page<FoodInfo> findAll(Pageable pageable) {
        return foodInfoRepository.findAll(pageable);
    }

    public Page<FoodInfo> findByFoodNameContains(Pageable pageable, String foodName) {
        return foodInfoRepository.findByFoodNameContains(foodName, pageable);
    }
}
