package calocheck.boundedContext.post.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final Rq rq;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/")
    public String showPostList(Model model) {
        List<Post> postList = postService.findAll();
        model.addAttribute("postList", postList);

        return "usr/post/list";
    }
    @GetMapping("/{postId}")
    public String showPostPage(Model model, @PathVariable Long postId) {
        List<Comment> commentList = commentService.findAllByPostId(postId);
        model.addAttribute("commentList", commentList);

        return "usr/post/post" + postId;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}")
    public String savePost(@PathVariable Long postId, String subject, String content) {
        if (subject == null || subject.length() == 0) {
            return rq.historyBack("제목을 입력해주세요.");
        }
        if (content == null || content.length() == 0) {
            return rq.historyBack("내용을 입력해주세요.");
        }

        RsData<Post> postRsData =
                postService.savePost(
                        subject,
                        content,
                        rq.getMember()
                );

        if (postRsData.isFail()) {
            return rq.historyBack(postRsData);
        }

        return rq.redirectWithMsg("/post/" + postId, postRsData);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        RsData<Post> deletePostRsData = postService.deletePost(postId, rq.getMember().getId());

        return rq.redirectWithMsg("/postId/" + postId, deletePostRsData);
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}/modify")
    public String modifyPost(@PathVariable Long postId, String modifyPost) {
        RsData<Post> modifyPostRsData = postService.modifyPost(postId, modifyPost, rq.getMember());

        return rq.redirectWithMsg("/post/" + postId, modifyPostRsData);
    }
}
