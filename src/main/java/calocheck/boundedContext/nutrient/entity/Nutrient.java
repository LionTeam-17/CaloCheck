package calocheck.boundedContext.nutrient.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
public class Nutrient extends BaseEntity {
    private String name;
    private Double value;
    private String unit;
}
