package calocheck.boundedContext.foodCart.repository;

import calocheck.boundedContext.foodCart.entity.FoodCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodCartRepository extends JpaRepository<FoodCart, Long> {
}
