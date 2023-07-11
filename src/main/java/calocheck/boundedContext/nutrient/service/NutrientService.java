package calocheck.boundedContext.nutrient.service;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.repository.NutrientBulkRepository;
import calocheck.boundedContext.nutrient.repository.NutrientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NutrientService {
    private final NutrientRepository nutrientRepository;
    private final NutrientBulkRepository nutrientBulkRepository;

    @Transactional
    public Nutrient create(FoodInfo foodInfo, String name, Double value) {
        Nutrient nutrient = Nutrient.builder()
                .foodInfo(foodInfo)
                .name(name)
                .value(value)
                .build();

        return nutrientRepository.save(nutrient);
    }

    @Transactional
    public Nutrient update(Nutrient nutrient, FoodInfo foodInfo, String name, Double value) {
        Nutrient updated = nutrient.toBuilder()
                .foodInfo(foodInfo)
                .name(name)
                .value(value)
                .build();

        return nutrientRepository.save(updated);
    }

    @Transactional
    public void delete(Nutrient nutrient) {
        nutrientRepository.delete(nutrient);
    }

    public Nutrient findById(Long id) {
        Optional<Nutrient> nutrient = nutrientRepository.findById(id);

        return nutrient.orElse(null);
    }

    public Nutrient findByNameAndValue(String name, double value) {
        Optional<Nutrient> nutrient = nutrientRepository.findByNameAndValue(name, value);

        return nutrient.orElse(null);
    }

    public void saveAll(List<Nutrient> nutrients) {
        nutrientBulkRepository.saveAll(nutrients);
    }

    @Transactional
    public void save(Nutrient nutrient){
        nutrientRepository.save(nutrient);
    }
}
