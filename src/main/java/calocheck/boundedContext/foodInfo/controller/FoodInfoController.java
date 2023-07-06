package calocheck.boundedContext.foodInfo.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/foodInfo")
@RequiredArgsConstructor
public class FoodInfoController {
    private final Rq rq;
    private final FoodInfoService foodInfoService;


    @GetMapping("/search")
    public String searchFoodInfo(Model model,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<FoodInfo> paging = null;

        switch (keyword) {
            case "": paging = foodInfoService.findAll(pageable); break;
            default: paging = foodInfoService.findByFoodNameContains(pageable, keyword); break;
        }

        List<FoodInfo> foodList = paging.getContent();

        model.addAttribute("paging", paging);
        model.addAttribute("foodList", foodList);
        model.addAttribute("keyword", keyword);

        return "usr/foodInfo/search";
    }

    @GetMapping("/details/{id}")
    public String showDetails(Model model, @PathVariable("id") Long id) {
        FoodInfo foodInfo = foodInfoService.findById(id);

        if (foodInfo == null) {
            return rq.historyBack("해당 음식 정보가 존재하지 않습니다.");
        }

        List<Nutrient> nutrients = foodInfo.getNutrientList();

        model.addAttribute("foodInfo", foodInfo);
        model.addAttribute("nutrients", nutrients);

        return "usr/foodInfo/details";
    }
}
