package calocheck.boundedContext.foodInfo.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.imageData.entity.ImageData;
import calocheck.boundedContext.imageData.imageTarget.ImageTarget;
import calocheck.boundedContext.imageData.service.ImageDataService;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.tag.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/foodInfo")
@RequiredArgsConstructor
public class FoodInfoController {
    private final Rq rq;
    private final FoodInfoService foodInfoService;
    private final ImageDataService imageDataService;

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
        List<Tag> tagList = foodInfoService.getTagList(foodInfo);
        Optional<ImageData> oImageData = imageDataService.findByImageTargetAndTargetId(ImageTarget.FOOD_IMAGE, id);

        model.addAttribute("foodInfo", foodInfo);
        model.addAttribute("nutrients", nutrients);
        model.addAttribute("tagList", tagList);
        oImageData.ifPresent(imageData -> model.addAttribute("imgData", imageDataService.getFoodDetailImage(imageData.getImageUrl())));

        return "usr/foodInfo/details";
    }
}
