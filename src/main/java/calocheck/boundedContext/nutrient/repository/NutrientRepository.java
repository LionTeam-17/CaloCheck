package calocheck.boundedContext.nutrient.repository;

import calocheck.boundedContext.nutrient.entity.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutrientRepository extends JpaRepository<Nutrient, Long> {

}
