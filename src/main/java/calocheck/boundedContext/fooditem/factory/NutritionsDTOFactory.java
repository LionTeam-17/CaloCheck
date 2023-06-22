package calocheck.boundedContext.fooditem.factory;

import calocheck.boundedContext.fooditem.dto.NutritionsDTO;

public class NutritionsDTOFactory {
    public static NutritionsDTO create(double carbohydrate, double fat, double protein,
                                                    double vitaminA, double vitaminB, double vitaminC, double vitaminD,
                                                    double cholesterol, double sodium, double potassium,
                                                    double sugar, double calcium, double iron) {
        NutritionsDTO nutritionsDTO = new NutritionsDTO();
        nutritionsDTO.setCarbohydrate(carbohydrate);
        nutritionsDTO.setFat(fat);
        nutritionsDTO.setProtein(protein);
        nutritionsDTO.setVitaminA(vitaminA);
        nutritionsDTO.setVitaminB(vitaminB);
        nutritionsDTO.setVitaminC(vitaminC);
        nutritionsDTO.setVitaminD(vitaminD);
        nutritionsDTO.setCholesterol(cholesterol);
        nutritionsDTO.setSodium(sodium);
        nutritionsDTO.setPotassium(potassium);
        nutritionsDTO.setSugar(sugar);
        nutritionsDTO.setCalcium(calcium);
        nutritionsDTO.setIron(iron);
        return nutritionsDTO;
    }
}