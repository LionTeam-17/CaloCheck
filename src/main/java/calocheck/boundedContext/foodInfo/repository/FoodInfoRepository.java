package calocheck.boundedContext.foodInfo.repository;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long> {
    Optional<FoodInfo> findById(Long id);
    List<FoodInfo> findAll();
    Page<FoodInfo> findAll(Pageable pageable);
    Page<FoodInfo> findByFoodNameContains(@Param("foodName") String foodName, Pageable pageable);
    Optional<FoodInfo> findByFoodName(String foodName);
    List<FoodInfo> findTop5ByFoodNameContains(String foodName);
    Optional<FoodInfo> findByFoodCode(String foodCode);
}
