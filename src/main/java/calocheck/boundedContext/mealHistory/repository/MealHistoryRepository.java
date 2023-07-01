package calocheck.boundedContext.mealHistory.repository;

import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MealHistoryRepository extends JpaRepository<MealHistory, Long> {

    Optional<MealHistory> findByMember (Member member);

}
