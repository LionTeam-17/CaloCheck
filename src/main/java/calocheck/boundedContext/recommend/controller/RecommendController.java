package calocheck.boundedContext.recommend.controller;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final PhotoService photoService;

    @GetMapping("/list")
    public String getRecommendList(){

        return "/usr/food/recommendList";
    }


    //FIXME 임시(다른 위치로 이동 필요)
    @PostMapping("/img")
    public String uploadImg(@RequestParam(required = false) MultipartFile img) throws IOException {

        RsData<String> isImg = photoService.isImgFile(img.getOriginalFilename());

        String photoUrl = "not image file";

        if(isImg.isSuccess()){
            //DB에 이미지 경로 저장 가능
            photoUrl = photoService.photoUpload(img);
        }else{
            System.out.println(isImg.getMsg());
        }

        photoService.vision(photoUrl);

        return "redirect:/recommend/list";
    }


}
