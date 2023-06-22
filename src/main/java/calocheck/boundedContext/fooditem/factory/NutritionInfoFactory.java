package calocheck.boundedContext.fooditem.factory;

import calocheck.boundedContext.fooditem.entity.NutritionInfo;;

public class NutritionInfoFactory {
    public static NutritionInfo createNutritionInfo(
            int kcal,
            double protein, double fat, double carbohydrate,
            double sugar, double fiber, double calcium,
            double iron, double magnesium, double potassium, double sodium,
            double vitaminA, double vitaminD, double vitaminB, double vitaminC,
            double cholesterol, double saturated, double transFattyAcid, double unSaturated
    ) {
        return NutritionInfo.builder()
                .kcal(kcal)
                .protein(protein).fat(fat).carbohydrate(carbohydrate)
                .sugar(sugar).fiber(fiber).calcium(calcium)
                .iron(iron).magnesium(magnesium).potassium(potassium).sodium(sodium)
                .vitaminA(vitaminA).vitaminD(vitaminD).vitaminB(vitaminB).vitaminC(vitaminC)
                .cholesterol(cholesterol).saturated(saturated).transFattyAcid(transFattyAcid).unSaturated(unSaturated)
                .build();
    }
}