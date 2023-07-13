package calocheck.boundedContext.cartFoodInfo.dto;

import calocheck.boundedContext.nutrient.dto.NutrientDTO;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private String result;
    private String msg;
    private Long deletedId;
    private Double totalKcal;
    private List<NutrientDTO> nutrients;

    public CartDTO(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public CartDTO(String result, String msg, Long deletedId) {
        this.result = result;
        this.msg = msg;
        this.deletedId = deletedId;
    }

    public CartDTO(String result, String msg, Double totalKcal, List<NutrientDTO> nutrients) {
        this.result = result;
        this.msg = msg;
        this.totalKcal = totalKcal;
        this.nutrients = nutrients;
    }

    public CartDTO(String result, String msg, Long deletedId, Double totalKcal, List<NutrientDTO> nutrients) {
        this.result = result;
        this.msg = msg;
        this.deletedId = deletedId;
        this.totalKcal = totalKcal;
        this.nutrients = nutrients;
    }
}