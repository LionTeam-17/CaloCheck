package calocheck.boundedContext.cartFoodInfo.repository;


import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartFoodInfoRepository extends JpaRepository<CartFoodInfo, Long> {
}
