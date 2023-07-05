package calocheck.boundedContext.dailyMenu.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DailyMenu extends BaseEntity {
    //매 기록마다의 식사(아침, 점심, 저녁, 기타)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodInfo foodInfo;
    private long quantity;
}