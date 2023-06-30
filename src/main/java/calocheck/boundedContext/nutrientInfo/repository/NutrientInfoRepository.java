package calocheck.boundedContext.nutrientInfo.repository;

import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientInfoRepository extends JpaRepository<NutrientInfo, Long> {
}
