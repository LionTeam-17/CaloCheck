package calocheck.boundedContext.imageData.imageTarget;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageTarget {

    POST_IMAGE("포스트 이미지"),
    FOOD_IMAGE("음식 이미지"),
    RECOMMEND_IMAGE("추천 이미지");

    private final String imageTarget;
}
