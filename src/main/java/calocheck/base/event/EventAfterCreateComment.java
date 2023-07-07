package calocheck.base.event;

import calocheck.boundedContext.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventAfterCreateComment {
    private final Comment comment;
}
