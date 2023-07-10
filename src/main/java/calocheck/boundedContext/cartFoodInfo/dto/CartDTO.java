package calocheck.boundedContext.cartFoodInfo.dto;

import lombok.Data;

@Data
public class CartDTO {
    private String result;
    private String msg;
    private Long foodId;

    public CartDTO(String result) {
        this.result = result;
    }

    public CartDTO(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public CartDTO(String result, String msg, Long foodId) {
        this.result = result;
        this.msg = msg;
        this.foodId = foodId;
    }
}
