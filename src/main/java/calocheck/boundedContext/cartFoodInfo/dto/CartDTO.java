package calocheck.boundedContext.cartFoodInfo.dto;

import lombok.Data;

@Data
public class CartDTO {
    private String result;
    private String msg;
    private Long deletedId;

    public CartDTO(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public CartDTO(String result, String msg, Long deletedId) {
        this.result = result;
        this.msg = msg;
        this.deletedId = deletedId;
    }
}