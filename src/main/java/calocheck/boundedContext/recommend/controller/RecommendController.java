package calocheck.boundedContext.recommend.controller;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.photo.service.PhotoService;
import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.service.RecommendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final PhotoService photoService;
    private final RecommendService recommendService;
    private final FoodInfoService foodInfoService;

    @GetMapping("/list")
    public String getRecommendList(Model model) {

        model.addAttribute("photoService", photoService);

        return "/usr/food/recommendList";
    }

//    @PostMapping("/list")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> postRecommendList
//            (@RequestParam Map<String, Object> params, HttpServletRequest req, HttpServletResponse res) {
//
//        Object selectedValue = params.get("selectedValue");
//        String selectedNutrition = selectedValue.toString();
//
//        Recommend recommendByName = recommendService.getRecommendByName(selectedNutrition);
//
//        List<String> recommendPhotoData = photoService.getRecommendPhotoData(recommendByName.getFoodList());
//
//        List<List<String>> top5ByFoodNameLists = foodInfoService.findTop5ByFoodNameContains(recommendByName.getFoodList());
//
//        Map<String, Object> result = new HashMap<String, Object>();
//
//        result.put("nutritionName", recommendByName.getNutritionName());
//        result.put("nutritionDescription", recommendByName.getDescription());
//        result.put("nutritionFoodList", recommendByName.getFoodList());
//        result.put("recommendPhotoData", recommendPhotoData);
//        result.put("top5ByFoodNameLists", top5ByFoodNameLists);
//
//        System.out.println("top5ByFoodNameLists = " + top5ByFoodNameLists.get(0));
//
//        return ResponseEntity.ok(result);
//    }

}
