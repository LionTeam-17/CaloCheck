package calocheck.boundedContext.fooditem.repository;

import calocheck.boundedContext.fooditem.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

}
