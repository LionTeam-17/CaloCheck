package calocheck.boundedContext.cartFoodInfo.dto;

import lombok.Data;

@Data
public class CartDTO {
    private String result;

    public CartDTO(String result) {
        this.result = result;
    }
}
