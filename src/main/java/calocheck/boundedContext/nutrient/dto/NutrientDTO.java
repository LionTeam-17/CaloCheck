package calocheck.boundedContext.nutrient.dto;

import calocheck.boundedContext.nutrient.entity.Nutrient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
public class NutrientDTO {
    private String name;
    private Double value;

    public void addValue(Nutrient nutrient) {
        this.value += nutrient.getValue();
    }
}
