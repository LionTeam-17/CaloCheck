package calocheck.boundedContext.nutrient.entity;

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
public class Nutrient extends BaseEntity {
    private String name;
    private Double value;
    private String unit;
}
