package calocheck.boundedContext.nutrientInfo.service;

import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;
import calocheck.boundedContext.nutrientInfo.factory.NutrientInfoFactory;
import calocheck.boundedContext.nutrientInfo.repository.NutrientInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NutrientInfoService {
    private final NutrientInfoRepository nutrientInfoRepository;

    public NutrientInfo create(
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

        NutrientInfo nutrientInfo = NutrientInfoFactory.createNutrientInfo(
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

        return nutrientInfoRepository.save(nutrientInfo);
    }

    public NutrientInfo update(NutrientInfo nutrientInfo,
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

        NutrientInfo updated = nutrientInfo.toBuilder()
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

        return nutrientInfoRepository.save(updated);
    }

    public void delete(NutrientInfo nutrientInfo) {
        nutrientInfoRepository.delete(nutrientInfo);
    }

    public NutrientInfo findById(Long id) {
        Optional<NutrientInfo> nutrientInfo = nutrientInfoRepository.findById(id);

        return nutrientInfo.orElse(null);
    }
}

