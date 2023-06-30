package calocheck.boundedContext.recommend.controller;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.photo.service.PhotoService;
import calocheck.boundedContext.recommend.config.RecommendConfig;
import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.service.RecommendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
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

    @GetMapping("/list")
    public String getRecommendList(Model model) {

        return "/usr/food/recommendList";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> postRecommendList
            (@RequestParam Map<String, Object> params, HttpServletRequest req, HttpServletResponse res) {

        Object selectedValue = params.get("selectedValue");
        String s = selectedValue.toString();

        Recommend recommendByName = recommendService.getRecommendByName(s);

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("nutritionName", recommendByName.getNutritionName());
        result.put("nutritionDescription", recommendByName.getDescription());
        result.put("nutritionFoodList", recommendByName.getFoodList());

        return ResponseEntity.ok(result);
    }

}
