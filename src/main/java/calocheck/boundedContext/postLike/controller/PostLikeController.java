package calocheck.boundedContext.postLike.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.postLike.entity.PostLike;
import calocheck.boundedContext.postLike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final Rq rq;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/postLike")
    public String savePostLike(@PathVariable Long postId) {
        RsData<PostLike> postLikeRsData = postLikeService.savePostLike(postId, rq.getMember());

        if (postLikeRsData.isFail()) {
            return rq.historyBack(postLikeRsData);
        }

        return rq.redirectWithMsg("/post/" + postId, postLikeRsData);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/postLike/delete")
    public String deletePostLike(@PathVariable Long postId) {
        RsData<PostLike> deletePostLike = postLikeService.deletePostLike(postId, rq.getMember());

        if (deletePostLike.isFail()) {
            return rq.historyBack(deletePostLike);
        }

        return rq.redirectWithMsg("/post/" + postId, deletePostLike);
    }
}
