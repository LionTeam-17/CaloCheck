package calocheck.boundedContext.post.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final Rq rq;
    private final PostService postService;


}
