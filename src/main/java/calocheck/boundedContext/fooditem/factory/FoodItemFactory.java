package calocheck.boundedContext.fooditem.factory;

import calocheck.boundedContext.fooditem.dto.NutritionsDTO;
import calocheck.boundedContext.fooditem.entity.FoodItem;

public class FoodItemFactory {
    public static FoodItem create(
            String foodName, int portionSize, String unit,
            double carbohydrate, double fat, double protein,
            double vitaminA, double vitaminB, double vitaminC, double vitaminD,
            double cholesterol, double sodium, double potassium,
            double sugar, double calcium, double iron) {

        return FoodItem.builder()
                .foodName(foodName).portionSize(portionSize).unit(unit)
                .carbohydrate(carbohydrate).fat(fat).protein(protein)
                .vitaminA(vitaminA).vitaminB(vitaminB).vitaminC(vitaminC).vitaminD(vitaminD)
                .cholesterol(cholesterol).sodium(sodium).potassium(potassium)
                .sugar(sugar).calcium(calcium).iron(iron)
                .build();
    }

    public static FoodItem create(String foodName, int portionSize, String unit,
                                  NutritionsDTO nutritionsDTO) {

        return FoodItem.builder()
                .foodName(foodName).portionSize(portionSize).unit(unit)
                .carbohydrate(nutritionsDTO.getCarbohydrate()).fat(nutritionsDTO.getFat()).protein(nutritionsDTO.getProtein())
                .vitaminA(nutritionsDTO.getVitaminA()).vitaminB(nutritionsDTO.getVitaminB()).vitaminC(nutritionsDTO.getVitaminC()).vitaminD(nutritionsDTO.getVitaminD())
                .cholesterol(nutritionsDTO.getCholesterol()).sodium(nutritionsDTO.getSodium()).potassium(nutritionsDTO.getPotassium())
                .sugar(nutritionsDTO.getSugar()).calcium(nutritionsDTO.getCalcium()).iron(nutritionsDTO.getIron())
                .build();
    }
}
