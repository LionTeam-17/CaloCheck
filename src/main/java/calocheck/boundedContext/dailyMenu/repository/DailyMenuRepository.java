package calocheck.boundedContext.dailyMenu.repository;

import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyMenuRepository extends JpaRepository<DailyMenu, Long> {

    List<DailyMenu> findByMemberAndCreateDateBetween(Member member, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
