package calocheck.boundedContext.recommend.controller;


import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.repository.RecommendRepository;
import calocheck.boundedContext.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private final RecommendService recommendService;
    private final PhotoService photoService;

    @GetMapping("/list")
    public String getRecommendList(@ModelAttribute("selectedValue") String selectedValue, Model model) {


        if (!selectedValue.isEmpty()) {
            Recommend nutrition = recommendService.getRecommendByName(selectedValue);

            //네트워크 측에서 모델에 들어간 nutrition 은 잘 받았지만, 화면에 내용이 출력되지 않고 있는 상황.
            model.addAttribute("nutrition", nutrition);
        }

        return "/usr/food/recommendList";
    }

    @PostMapping("/list")
    public String postRecommendList(@RequestBody String selectedValue, RedirectAttributes redirectAttributes) {

        //redirect 를 하고, get 요청이 이루어 졌을 때 내용을 사용할 수 있도록 함.
        redirectAttributes.addFlashAttribute("selectedValue", selectedValue);
        return "redirect:/recommend/list";
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

        return "redirect:/recommend/list";
    }


}
