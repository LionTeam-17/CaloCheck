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

    @GetMapping("")
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

        return "usr/post/post";
    }

    // @PreAuthorize("isAuthenticated()")
    // todo 로그인 연결 시 주석 해제
    @GetMapping("/createForm")
    public String savePost() {
        return "usr/post/createForm";
    }

    // @PreAuthorize("isAuthenticated()")
    // todo 로그인 연결 시 주석 해제
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

        return rq.redirectWithMsg("/post", postRsData);
    }

    // todo UI와 연결
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        RsData<Post> deletePostRsData = postService.deletePost(postId, rq.getMember().getId());

        return rq.redirectWithMsg("/postId/" + postId, deletePostRsData);
    }

    // todo UI와 연결
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{postId}/modify")
    public String modifyPost(@PathVariable Long postId, String modifyPost) {
        RsData<Post> modifyPostRsData = postService.modifyPost(postId, modifyPost, rq.getMember());

        return rq.redirectWithMsg("/post/" + postId, modifyPostRsData);
    }
}
