package calocheck.boundedContext.nutrient.service;

import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.repository.NutrientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NutrientService {
    private final NutrientRepository nutrientRepository;

    public Nutrient create(String name, Double value, String unit) {
        Nutrient nutrient = Nutrient.builder()
                .name(name)
                .value(value)
                .unit(unit)
                .build();

        return nutrientRepository.save(nutrient);
    }

    public Nutrient update(Nutrient nutrient, String name, Double value, String unit) {
        Nutrient updated = nutrient.toBuilder()
                .name(name)
                .value(value)
                .unit(unit)
                .build();

        return nutrientRepository.save(updated);
    }

    public void delete(Nutrient nutrient) {
        nutrientRepository.delete(nutrient);
    }

    public Nutrient findById(Long id) {
        Optional<Nutrient> nutrient = nutrientRepository.findById(id);

        return nutrient.orElse(null);
    }
}
