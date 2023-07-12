package calocheck.boundedContext.recommend.repository;

import calocheck.boundedContext.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RecommendRepository extends JpaRepository<Recommend, Long>{

    Optional<Recommend> findByNutritionName(String NutritionName);

}
