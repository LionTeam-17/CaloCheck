package calocheck.boundedContext.post.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final Rq rq;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String showPostList(@RequestParam(defaultValue = "0") int page, Model model) {
        List<Post> postList = postService.findAll();
        Page<Post> paging = postService.getList(page);

        model.addAttribute("postList", postList);
        model.addAttribute("paging", paging);

        return "usr/post/list";
    }

    @GetMapping("/{postId}")
    public String showPostPage(Model model, @PathVariable Long postId) {
        Optional<Post> oPost = postService.findById(postId);
        oPost.ifPresent(post -> model.addAttribute("post", post));

        List<Comment> commentList = commentService.findAllByPostId(postId);
        model.addAttribute("commentList", commentList);

        return "usr/post/postDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/createForm")
    public String savePost() {
        return "usr/post/createForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createForm")
    public String savePost(String iSubject, String iContent) {
        if (iSubject == null || iSubject.length() == 0) {
            return rq.historyBack("제목을 입력해주세요.");
        }
        if (iContent == null || iContent.length() == 0) {
            return rq.historyBack("내용을 입력해주세요.");
        }

        RsData<Post> postRsData =
                postService.savePost(
                        iSubject,
                        iContent,
                        rq.getMember()
                );

        if (postRsData.isFail()) {
            return rq.historyBack(postRsData);
        }

        return rq.redirectWithMsg("/post/list", postRsData);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        RsData<Post> deletePostRsData = postService.deletePost(postId, rq.getMember().getId());

        if (deletePostRsData.isFail()) {
            return rq.historyBack(deletePostRsData);
        }

        return rq.redirectWithMsg("/post/list", deletePostRsData);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{postId}/modify")
    public String modifyPost(@PathVariable Long postId) {
        return "usr/post/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}/modify")
    public String modifyPost(@PathVariable Long postId, String iModifySubject, String iModifyContent) {
        RsData<Post> modifyPostRsData = postService.modifyPost(postId, iModifySubject, iModifyContent, rq.getMember());

        if (modifyPostRsData.isFail()) {
            return rq.historyBack(modifyPostRsData);
        }

        return rq.redirectWithMsg("/post/" + postId, modifyPostRsData);
    }
}
