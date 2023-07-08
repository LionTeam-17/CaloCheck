package calocheck.boundedContext.recommend.controller;

import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.image.entity.Image;
import calocheck.boundedContext.image.service.ImageService;
import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.service.RecommendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final ImageService imageService;
    private final RecommendService recommendService;
    private final FoodInfoService foodInfoService;

    @GetMapping("/list")
    public String getRecommendList(Model model) {

        model.addAttribute("photoService", imageService);

        return "/usr/food/recommendList";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> postRecommendList
            (@RequestParam Map<String, Object> params, HttpServletRequest req, HttpServletResponse res) {

        Object selectedValue = params.get("selectedValue");
        String selectedNutrition = selectedValue.toString();

        Recommend recommendByName = recommendService.getRecommendByName(selectedNutrition);

        List<Image> recommendPhotoData = imageService.getRecommendImageList(recommendByName.getFoodList());

        List<List<String>> top5ByFoodNameLists = foodInfoService.findTop5ByFoodNameContains(recommendByName.getFoodList());

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("nutritionName", recommendByName.getNutritionName());
        result.put("nutritionDescription", recommendByName.getDescription());
        result.put("nutritionFoodList", recommendByName.getFoodList());
        result.put("recommendPhotoData", recommendPhotoData);
        result.put("top5ByFoodNameLists", top5ByFoodNameLists);

        System.out.println("top5ByFoodNameLists = " + top5ByFoodNameLists.get(0));

        return ResponseEntity.ok(result);
    }

}
