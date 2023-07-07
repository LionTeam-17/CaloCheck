package calocheck.base.event;

import calocheck.boundedContext.postLike.entity.PostLike;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventAfterCreatePostLike {
    private final PostLike postLike;
}
