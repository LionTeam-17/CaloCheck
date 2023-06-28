package calocheck.boundedContext.dailyMenu.repository;

import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyMenuRepository extends JpaRepository<DailyMenu, Long> {
}