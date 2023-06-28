package calocheck.boundedContext.nutrientInfo.factory;

import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;;

public class NutrientInfoFactory {
    public static NutrientInfo createNutrientInfo(
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

        return NutrientInfo.builder()
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