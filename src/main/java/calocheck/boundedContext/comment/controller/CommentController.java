package calocheck.boundedContext.comment.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
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

    //    @PreAuthorize("isAuthenticated()")
    // todo login 구현 시 해제
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

    // todo UI와 연결
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        RsData<Comment> deleteCmtRsData = commentService.deleteComment(commentId, rq.getMember().getId());

        return rq.redirectWithMsg("/post/" + postId, deleteCmtRsData);
    }

    // todo UI와 연결
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}/comment/{commentId}/modify")
    public String modifyComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            String modifyComment
    ) {
        RsData<Comment> modifyCommentRsData =
                commentService.modifyComment(commentId, modifyComment, rq.getMember());

        return rq.redirectWithMsg("/post/" + postId, modifyCommentRsData);
    }
}
