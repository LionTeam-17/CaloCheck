package calocheck.boundedContext.cartFoodInfo.dto;

import lombok.Data;

@Data
public class CartDTO {
    private String result;
    private String msg;

    public CartDTO(String result) {
        this.result = result;
    }

    public CartDTO(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }
}
