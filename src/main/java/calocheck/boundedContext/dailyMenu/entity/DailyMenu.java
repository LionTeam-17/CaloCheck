package calocheck.boundedContext.dailyMenu.entity;

import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DailyMenu {

    //매 기록마다의 식사(아침, 점심, 저녁, 기타)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MealHistory mealHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    private FoodInfo foodInfo;

    private double quantity;
}