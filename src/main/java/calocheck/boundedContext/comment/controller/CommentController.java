package calocheck.boundedContext.comment.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final Rq rq;
    private final CommentService commentService;

}
