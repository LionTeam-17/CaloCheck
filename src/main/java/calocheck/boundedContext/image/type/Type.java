package calocheck.boundedContext.image.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {

    POST_IMAGE("포스트 이미지"), FOOD_IMAGE("음식 이미지");
    private final String imageType;

}
