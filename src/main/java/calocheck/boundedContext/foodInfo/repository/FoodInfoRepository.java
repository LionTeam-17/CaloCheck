package calocheck.boundedContext.foodInfo.repository;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long> {

}
