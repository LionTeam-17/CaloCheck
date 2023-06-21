package calocheck.boundedContext.fooditem.service;

import calocheck.boundedContext.fooditem.dto.NutritionsDTO;
import calocheck.boundedContext.fooditem.entity.FoodItem;
import calocheck.boundedContext.fooditem.factory.FoodItemFactory;
import calocheck.boundedContext.fooditem.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;

    public FoodItem create(
            String foodName, int portionSize, String unit,
            NutritionsDTO nutritionsDTO) {

        FoodItem foodItem = FoodItemFactory.create(
                foodName, portionSize, unit,
                nutritionsDTO.getCarbohydrate(), nutritionsDTO.getFat(), nutritionsDTO.getProtein(),
                nutritionsDTO.getVitaminA(), nutritionsDTO.getVitaminB(), nutritionsDTO.getVitaminC(), nutritionsDTO.getVitaminD(),
                nutritionsDTO.getCholesterol(), nutritionsDTO.getSodium(), nutritionsDTO.getPotassium(),
                nutritionsDTO.getSugar(), nutritionsDTO.getCalcium(), nutritionsDTO.getIron());

        return foodItemRepository.save(foodItem);
    }

    public FoodItem update(FoodItem foodItem,
            String foodName, int portionSize, String unit,
            NutritionsDTO nutritionsDTO) {

        FoodItem updatedItem = foodItem.toBuilder()
                .foodName(foodName).portionSize(portionSize).unit(unit)
                .carbohydrate(nutritionsDTO.getCarbohydrate()).fat(nutritionsDTO.getFat()).protein(nutritionsDTO.getProtein())
                .vitaminA(nutritionsDTO.getVitaminA()).vitaminB(nutritionsDTO.getVitaminB()).vitaminC(nutritionsDTO.getVitaminC()).vitaminD(nutritionsDTO.getVitaminD())
                .cholesterol(nutritionsDTO.getCholesterol()).sodium(nutritionsDTO.getSodium()).potassium(nutritionsDTO.getPotassium())
                .sugar(nutritionsDTO.getSugar()).calcium(nutritionsDTO.getCalcium()).iron(nutritionsDTO.getIron())
                .build();

        return foodItemRepository.save(foodItem);
    }

    public void delete(FoodItem foodItem) {
        foodItemRepository.delete(foodItem);
    }

    public FoodItem findById(Long id) {
        Optional<FoodItem> foodItem = foodItemRepository.findById(id);

        return foodItem.orElse(null);
    }
}
