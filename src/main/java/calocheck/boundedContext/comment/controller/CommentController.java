package calocheck.boundedContext.comment.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class CommentController {
    private final Rq rq;
    private final CommentService commentService;
    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}")
    public String saveComment(@PathVariable Long postId, String iComment) {
        if (iComment == null || iComment.length() == 0) {
            return rq.historyBack("댓글을 입력해주세요.");
        }

        RsData<Comment> commentRsData =
                commentService.saveComment(
                        iComment,
                        postService.findById(postId).get(),
                        rq.getMember()
                );

        if (commentRsData.isFail()) {
            return rq.historyBack(commentRsData);
        }

        return rq.redirectWithMsg("/post/" + postId, commentRsData);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        RsData<Comment> deleteCmtRsData = commentService.deleteComment(commentId, rq.getMember().getId());

        if (deleteCmtRsData.isFail()) {
            return rq.historyBack(deleteCmtRsData);
        }
        return rq.redirectWithMsg("/post/" + postId, deleteCmtRsData);
    }
}
