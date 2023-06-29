package calocheck.boundedContext.nutrient.repository;

import calocheck.boundedContext.nutrient.entity.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepository extends JpaRepository<Nutrient, Long> {
}
