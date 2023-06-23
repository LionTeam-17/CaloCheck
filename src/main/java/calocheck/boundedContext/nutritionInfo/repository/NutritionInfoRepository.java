package calocheck.boundedContext.nutritionInfo.repository;

import calocheck.boundedContext.nutritionInfo.entity.NutritionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionInfoRepository extends JpaRepository<NutritionInfo, Long> {
}
