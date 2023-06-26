package calocheck.boundedContext.foodInfo.controller;

import calocheck.base.rq.Rq;
import calocheck.base.util.FoodDataExtractor;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.nutritionInfo.entity.NutritionInfo;
import calocheck.boundedContext.nutritionInfo.service.NutritionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/foodInfo")
@RequiredArgsConstructor
public class FoodInfoController {
    private final Rq rq;
    private final FoodInfoService foodInfoService;
    private final NutritionInfoService nutritionInfoService;
    private final FoodDataExtractor foodDataExtractor;


    @GetMapping("/search")
    public String foodSearch(Model model,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<FoodInfo> paging = foodInfoService.findAll(pageable);
        List<FoodInfo> foodList = paging.getContent();

        model.addAttribute("paging", paging);
        model.addAttribute("foodList", foodList);
        model.addAttribute("keyword", keyword);

        return "usr/foodInfo/search";
    }

    @GetMapping("/details/{id}")
    public String foodDetails(Model model, @PathVariable("id") Long id) {
        FoodInfo foodInfo = foodInfoService.findById(id);

        if (foodInfo == null) {
            return rq.historyBack("해당 음식 정보가 존재하지 않습니다.");
        }

        NutritionInfo nutritionInfo = foodInfo.getNutritionInfo();

        model.addAttribute("foodInfo", foodInfo);
        model.addAttribute("nutritions", nutritionInfo);

        return "usr/foodInfo/details";
    }

    @GetMapping("/excel-data-save")
    @ResponseBody
    public String test() {
        try {
            foodDataExtractor.readFile();
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }
}
