package calocheck.boundedContext.fooditem.entity;

import calocheck.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NutritionInfo extends BaseEntity {
    private int kcal; // 에너지(kcal)
    private double protein; // 단백질
    private double fat; // 지방
    private double carbohydrate; // 탄수화물
    private double sugar; // 당류
    private double fiber; // 식이섬유
    private double calcium; // 칼슘
    private double iron; // 철
    private double magnesium; // 마그네슘
    private double potassium; // 칼륨
    private double sodium; // 나트륨
    private double vitaminA;
    private double vitaminD;
    private double vitaminB;
    private double vitaminC;
    private double cholesterol; // 콜레스테롤
    private double saturated; // 포화 지방산
    private double transFattyAcid; // 트랜스 지방산
    private double unSaturated; // 불포화 지방산
}