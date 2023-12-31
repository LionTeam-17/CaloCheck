package calocheck.boundedContext.nutrient.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Nutrient extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_code", referencedColumnName = "foodCode")
    private FoodInfo foodInfo;
    private String name;
    private Double value;
    public void addValue(Nutrient nutrient) {
        this.value += nutrient.getValue();
    }
}