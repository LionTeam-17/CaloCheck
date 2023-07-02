package calocheck.boundedContext.post.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.photo.config.S3Config;
import calocheck.boundedContext.photo.service.PhotoService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final Rq rq;
    private final PostService postService;
    private final CommentService commentService;
    private final PhotoService photoService;
    private final FoodInfoService foodInfoService;

    @GetMapping("/list")
    public String showPostList(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(value = "kw", defaultValue = "") String kw,
                               @RequestParam(value = "code", defaultValue = "0") String code,
                               Model model) {

        if (!kw.equals("")) {
            if (code.equals("0")) {
                List<Post> filteringList = postService.findBySubjectLikeOrMemberNicknameLike
                        (
                                "%" + kw + "%",
                                "%" + kw + "%"
                        );
                Page<Post> filteringPaging = postService.findBySubjectLikeOrMemberNicknameLike
                        (
                                "%" + kw + "%",
                                "%" + kw + "%",
                                page
                        );

                model.addAttribute("postList", filteringList);
                model.addAttribute("paging", filteringPaging);
            } else {
                List<Post> topFilteringList = postService.findBySubjectLikeOrMemberNicknameLikeOrderByPopularityDesc
                        (
                                "%" + kw + "%",
                                "%" + kw + "%"
                        );
                Page<Post> topFilteringPaging = postService.findBySubjectLikeOrMemberNicknameLikeOrderByPopularityDesc
                        (
                                "%" + kw + "%",
                                "%" + kw + "%",
                                page
                        );

                model.addAttribute("postList", topFilteringList);
                model.addAttribute("paging", topFilteringPaging);
            }
        } else {
            if (code.equals("0")) {
                List<Post> postList = postService.findAll();
                Page<Post> paging = postService.getList(page);

                model.addAttribute("postList", postList);
                model.addAttribute("paging", paging);

            } else {
                List<Post> topPostList = postService.findByOrderByPopularityDesc();
                Page<Post> paging = postService.findByOrderByPopularityDesc(page);

                model.addAttribute("postList", topPostList);
                model.addAttribute("paging", paging);
            }
        }
        model.addAttribute("code", code);
        model.addAttribute("kw", kw);

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
    public String savePost(String iSubject, String iContent,
                           @RequestParam(required = false) MultipartFile img,
                           String selectedFood, boolean agree) throws IOException {

        if (iSubject == null || iSubject.length() == 0) {
            return rq.historyBack("제목을 입력해주세요.");
        }
        if (iContent == null || iContent.length() == 0) {
            return rq.historyBack("내용을 입력해주세요.");
        }

        //S-6 => 이미지 파일일 경우, S-7 => 첨부 파일이 없는 경우(default 이미지 적용)
        RsData<String> isImgRsData = photoService.isImgFile(img.getOriginalFilename());

        String photoUrl = S3Config.getSampleImg();

        if (isImgRsData.getResultCode().equals("S-6")) {

            //S3 Bucket 에 이미지 업로드 및 경로 재대입
            photoUrl = photoService.photoUpload(img);

            //업로드된 이미지가 안전한 이미지인지 확인
            RsData<String> isSafeImg = photoService.detectSafeSearchRemote(photoUrl);

            if (isSafeImg.isFail()) {
                return rq.historyBack(isSafeImg);
            }

        } else if (isImgRsData.isFail()) {
            //첨부파일이 올바르지 않습니다.
            return rq.historyBack(isImgRsData);
        }

        RsData<String> isFoodImg = photoService.detectLabelsRemote(photoUrl);

//        //이미지 수집 동의시, 음식과 연결
//        if (isFoodImg.isSuccess() && agree) {
//
//            //TODO 음식 이미지이며, 안전한 것이 확인되었으므로 food info db에 주소 추가(장바구니 구현 후 추가 예정)
//            FoodInfo byFoodName = foodInfoService.findByFoodName(selectedFood);
//
//            if(byFoodName!=null && byFoodName.photoUrl != null){
//                byFoodName.updatePhotoUrl(photoUrl)
//            }
//
//        }

        RsData<Post> postRsData =
                postService.savePost(
                        iSubject,
                        iContent,
                        photoUrl,
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

