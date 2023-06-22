package calocheck.boundedContext.fooditem.repository;

import calocheck.boundedContext.fooditem.entity.FoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long> {

}
