package calocheck.boundedContext.nutrientInfo.service;

import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.service.NutrientService;
import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;
import calocheck.boundedContext.nutrientInfo.repository.NutrientInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NutrientInfoService {
    private final NutrientInfoRepository nutrientInfoRepository;
    private final NutrientService nutrientService;

    public NutrientInfo create(
            double kcal, List<Nutrient> nutrientList) {

        NutrientInfo nutrientInfo = NutrientInfo.builder()
                .kcal(kcal)
                .nutrientList(nutrientList)
                .build();

        return nutrientInfoRepository.save(nutrientInfo);
    }

    public NutrientInfo update(NutrientInfo nutrientInfo, double kcal, List<Nutrient> nutrientList) {

        NutrientInfo updated = nutrientInfo.toBuilder()
                .kcal(kcal)
                .nutrientList(nutrientList)
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

    public void addNutrientData(List<Nutrient> total, List<Nutrient> nutrients) {
        for (int i = 0; i < total.size(); i++) {
            Nutrient temp = total.get(i);
            Nutrient nutrient = nutrients.get(i);
            double value = temp.getValue() + nutrient.getValue();
            total.set(i, temp.toBuilder().value(value).build());
        }
    }
}

