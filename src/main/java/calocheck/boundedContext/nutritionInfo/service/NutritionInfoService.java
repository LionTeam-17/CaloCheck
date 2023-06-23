package calocheck.boundedContext.nutritionInfo.service;

import calocheck.boundedContext.nutritionInfo.entity.NutritionInfo;
import calocheck.boundedContext.nutritionInfo.factory.NutritionInfoFactory;
import calocheck.boundedContext.nutritionInfo.repository.NutritionInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NutritionInfoService {
    private final NutritionInfoRepository nutritionInfoRepository;

    public NutritionInfo create(
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

        NutritionInfo nutritionInfo = NutritionInfoFactory.createNutritionInfo(
                kcal,
                protein,
                fat,
                carbohydrate,
                sugar,
                fiber,
                calcium,
                iron,
                magnesium,
                potassium,
                sodium,
                cholesterol,
                saturated,
                transFattyAcid,
                unSaturated
        );

        return nutritionInfoRepository.save(nutritionInfo);
    }

    public NutritionInfo update(NutritionInfo nutritionInfo,
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

        NutritionInfo updated = nutritionInfo.toBuilder()
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

        return nutritionInfoRepository.save(updated);
    }

    public void delete(NutritionInfo nutritionInfo) {
        nutritionInfoRepository.delete(nutritionInfo);
    }

    public NutritionInfo findById(Long id) {
        Optional<NutritionInfo> nutritionInfo = nutritionInfoRepository.findById(id);

        return nutritionInfo.orElse(null);
    }
}

