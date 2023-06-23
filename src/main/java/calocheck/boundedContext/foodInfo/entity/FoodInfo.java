package calocheck.boundedContext.foodInfo.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.nutritionInfo.entity.NutritionInfo;
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
public class FoodInfo extends BaseEntity {
    @OneToOne
    private NutritionInfo nutritionInfo; // 영양 정보
    private String foodName; // 식품명
    private String manufacturer; // 제조사/유통사
    private String category; // 식품대분류
    private int portionSize; // 1회 제공량
    private String unit; // 내용량 단위
    private int totalSize; // 총 내용량
}
