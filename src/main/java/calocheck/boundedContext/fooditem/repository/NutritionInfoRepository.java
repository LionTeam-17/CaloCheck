package calocheck.boundedContext.fooditem.repository;

import calocheck.boundedContext.fooditem.entity.NutritionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionInfoRepository extends JpaRepository<NutritionInfo, Long> {
}
