package calocheck.boundedContext.post.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.dailyMenu.service.DailyMenuService;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.imageData.entity.ImageData;
import calocheck.boundedContext.imageData.imageTarget.ImageTarget;
import calocheck.boundedContext.imageData.service.ImageDataService;
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
    private final ImageDataService imageDataService;
    private final FoodInfoService foodInfoService;
    private final DailyMenuService dailyMenuService;

    @GetMapping("/list")
    public String showPostList(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(value = "kw", defaultValue = "") String kw,
                               @RequestParam(value = "code", defaultValue = "0") String code,
                               @RequestParam(value = "postType", defaultValue = "A") String postType,
                               Model model) {

        Page<Post> paging;
        if (postType.equals("A")) {
            if (!kw.equals("")) {
                if (code.equals("0")) {
                    paging = postService.findByPostTypeAndSubjectLikeOrMemberNicknameLike
                            ("A", "%" + kw + "%", "%" + kw + "%", page);
                } else {
                    paging = postService.findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc
                            ("A", "%" + kw + "%", "%" + kw + "%", page);
                }
            } else {
                if (code.equals("0")) paging = postService.findByPostType("A", page);
                else paging = postService.findByPostTypeOrderByPopularityDesc("A", page);
            }
        } else {
            if (!kw.equals("")) {
                if (code.equals("0")) {
                    paging = postService.findByPostTypeAndSubjectLikeOrMemberNicknameLike
                            ("B", "%" + kw + "%", "%" + kw + "%", page);
                } else {
                    paging = postService.findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc
                            ("B", "%" + kw + "%", "%" + kw + "%", page);
                }
            } else {
                if (code.equals("0")) paging = postService.findByPostType("B", page);
                else paging = postService.findByPostTypeOrderByPopularityDesc("B", page);
            }
        }
        model.addAttribute("postType", postType);
        model.addAttribute("paging", paging);
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

        Optional<ImageData> oImageData = imageDataService.findByImageTargetAndTargetId(ImageTarget.POST_IMAGE, postId);

        oImageData.ifPresent(imageData -> model.addAttribute("postImage", imageDataService.imageProcessing(imageData)));

        return "usr/post/postDetail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/createForm")
    public String savePost(Model model) {

        List<String> todayFoodNameList = dailyMenuService.getTodayFoodNameList(rq.getMember());

        model.addAttribute("todayFoodNameList", todayFoodNameList);

        return "usr/post/createForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createForm")
    public String savePost(String iSubject, String iContent, String iPostType,
                           @RequestParam(required = false) MultipartFile img,
                           String selectedFood) throws IOException {

        if (iSubject == null || iSubject.length() == 0) {
            return rq.historyBack("제목을 입력해주세요.");
        }
        if (iContent == null || iContent.length() == 0) {
            return rq.historyBack("내용을 입력해주세요.");
        }

        //S-6 => 이미지 파일일 경우, S-7 => 첨부 파일이 없는 경우 null
        RsData<ImageData> isImgRsData = imageDataService.isImgFile(img.getOriginalFilename());

        //이미지 파일이 들어온 경우에만 경로 대입, 이미지 검사 가능
        String imageUrl = null;

        if (isImgRsData.getResultCode().equals("S-6")) {

            //S3 Bucket 에 이미지 업로드 및 경로 재대입, 이미지 검사
            imageUrl = imageDataService.imageUpload(img, ImageTarget.POST_IMAGE);
            RsData<ImageData> detectedLabelRsData = imageDataService.detectLabelsRemote(imageUrl);
            RsData<ImageData> safeSearchRsData = imageDataService.detectSafeSearchRemote(imageUrl);

            //세이프 서치를 통과하지 못한 경우에는 음식 등록 외에 글 작성도 불가
            if (safeSearchRsData != null && safeSearchRsData.isFail()) {
                return rq.historyBack(safeSearchRsData);
            }

            //음식을 선택 했지만, 음식 이미지가 아닌 경우
            if (detectedLabelRsData != null && detectedLabelRsData.isFail() && selectedFood != null) {
                return rq.historyBack(detectedLabelRsData);
            }

            //음식을 선택 했고, 음식 이미지로 등록 가능한 경우
            if (detectedLabelRsData != null && safeSearchRsData != null
                    && detectedLabelRsData.isSuccess() && safeSearchRsData.isSuccess() && selectedFood != null) {

                Long foodId = foodInfoService.findByFoodName(selectedFood).getId();

                Optional<ImageData> oFoodImage = imageDataService.findByImageTargetAndTargetId(ImageTarget.FOOD_IMAGE, foodId);

                if (oFoodImage.isEmpty()) {
                    imageUrl = imageDataService.imageUpload(img, ImageTarget.FOOD_IMAGE);
                    RsData<ImageData> imageRsData = imageDataService.createImageData(ImageTarget.FOOD_IMAGE, imageUrl, foodId);

                    if (imageRsData.isFail()) {
                        return rq.historyBack(imageRsData);
                    }
                }

            }

        } else if (isImgRsData.isFail()) {
            return rq.historyBack(isImgRsData);
        }

        RsData<Post> postRsData = postService.savePost(iSubject, iContent, iPostType, rq.getMember());

        if (postRsData.isFail()) {
            return rq.historyBack(postRsData);
        }

        if (imageUrl != null) {

            RsData<ImageData> imageRsData = imageDataService.createImageData(ImageTarget.POST_IMAGE, imageUrl, postRsData.getData().getId());

            if (imageRsData.isFail()) {
                return rq.historyBack(imageRsData);
            }
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
    public String modifyPost(@PathVariable Long postId,
                             String iModifySubject,
                             String iModifyContent,
                             String iModifyPostType,
                             @RequestParam(required = false) MultipartFile iModifyImg) throws IOException {

        RsData<ImageData> isImgRsData = imageDataService.isImgFile(iModifyImg.getOriginalFilename());

        String imageUrl = null;

        if (isImgRsData.getResultCode().equals("S-6")) {

            //S3 Bucket 에 이미지 업로드 및 경로 재대입
            imageUrl = imageDataService.imageUpload(iModifyImg, ImageTarget.POST_IMAGE);

            //업로드된 이미지가 안전한 이미지인지 확인
            RsData<ImageData> isSafeImg = imageDataService.detectSafeSearchRemote(imageUrl);

            if (isSafeImg.isFail()) {
                return rq.historyBack(isSafeImg);
            }

        } else if (isImgRsData.isFail()) {
            //첨부파일이 올바르지 않습니다.
            return rq.historyBack(isImgRsData);
        }

        RsData<Post> modifyPostRsData = postService.modifyPost(postId, iModifySubject, iModifyContent, iModifyPostType, rq.getMember());

        if (modifyPostRsData.isFail()) {
            return rq.historyBack(modifyPostRsData);
        }

        return rq.redirectWithMsg("/post/" + postId, modifyPostRsData);
    }
}
