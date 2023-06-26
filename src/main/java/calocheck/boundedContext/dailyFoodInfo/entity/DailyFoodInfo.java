package calocheck.boundedContext.dailyFoodInfo.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DailyFoodInfo extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private DailyMenu dailyMenu;
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodInfo foodInfo;
}