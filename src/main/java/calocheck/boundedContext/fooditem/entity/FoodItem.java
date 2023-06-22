package calocheck.boundedContext.fooditem.entity;

import calocheck.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem extends BaseEntity {
    String foodName;
    int portionSize;
    String unit;
    double carbohydrate;
    double fat;
    double protein;
    double vitaminA;
    double vitaminB;
    double vitaminC;
    double vitaminD;
    double cholesterol;
    double sodium;
    double potassium;
    double sugar;
    double calcium;
    double iron;
}
