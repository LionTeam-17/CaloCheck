package calocheck.boundedContext.nutritionInfo.factory;

import calocheck.boundedContext.nutritionInfo.entity.NutritionInfo;;

public class NutritionInfoFactory {
    public static NutritionInfo createNutritionInfo(
            double kcal,
            double protein,
            double fat,
            double carbohydrate,
            double sugar,
            double fiber,
            double calcium,
            double iron,
            double magnesium,
            double potassium,
            double sodium,
            double cholesterol,
            double saturated,
            double transFattyAcid,
            double unSaturated) {

        return NutritionInfo.builder()
                .kcal(kcal)
                .protein(protein)
                .fat(fat)
                .carbohydrate(carbohydrate)
                .sugar(sugar)
                .fiber(fiber)
                .calcium(calcium)
                .iron(iron)
                .magnesium(magnesium)
                .potassium(potassium)
                .sodium(sodium)
                .cholesterol(cholesterol)
                .saturated(saturated)
                .transFattyAcid(transFattyAcid)
                .unSaturated(unSaturated)
                .build();
    }
}