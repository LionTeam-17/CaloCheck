package calocheck.boundedContext.foodInfo.controller;

import calocheck.base.rq.Rq;
import calocheck.base.util.excel.service.ExcelService;
import calocheck.base.util.s3.service.S3Service;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.imageData.entity.ImageData;
import calocheck.boundedContext.imageData.imageTarget.ImageTarget;
import calocheck.boundedContext.imageData.service.ImageDataService;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.tag.entity.Tag;
import calocheck.boundedContext.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/foodInfo")
@RequiredArgsConstructor
public class FoodInfoController {
    private final Rq rq;
    private final FoodInfoService foodInfoService;
    private final ImageDataService imageDataService;
    private final TagService tagService;
    private final S3Service s3Service;
    private final ExcelService excelService;

    @GetMapping("/search")
    public String searchFoodInfo(Model model,
                                 @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<FoodInfo> paging = null;

        switch (keyword) {
            case "":
                paging = foodInfoService.findAll(pageable);
                break;
            default:
                paging = foodInfoService.findByFoodNameContains(pageable, keyword);
                break;
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
        List<Tag> tagList = tagService.getTagList(foodInfo);
        Optional<ImageData> oImageData = imageDataService.findByImageTargetAndTargetId(ImageTarget.FOOD_IMAGE, id);

        model.addAttribute("foodInfo", foodInfo);
        model.addAttribute("nutrients", nutrients);
        model.addAttribute("tagList", tagList);
        oImageData.ifPresent(imageData -> model.addAttribute("imgData", imageDataService.imageProcessing(imageData)));

        return "usr/foodInfo/details";
    }

    @GetMapping("/handleLambda")
    @ResponseBody
    public String handleLambda(@RequestParam(name = "bucket", defaultValue = "calocheck-data") String bucket, @RequestParam(name = "key", defaultValue = "food-data/통합 식품영양성분DB_가공식품1.xlsx") String key) throws IOException {
        String [] nameList = {
                "food-data/통합 식품영양성분DB_가공식품1.xlsx",
                "food-data/통합 식품영양성분DB_가공식품2.xlsx",
                "food-data/통합 식품영양성분DB_가공식품3.xlsx",
                "food-data/통합 식품영양성분DB_가공식품4.xlsx",
                "food-data/통합 식품영양성분DB_가공식품5.xlsx",
                "food-data/통합 식품영양성분DB_가공식품6.xlsx",
                "food-data/통합 식품영양성분DB_가공식품7.xlsx",
                "food-data/통합 식품영양성분DB_가공식품8.xlsx"
        };


        for (String name : nameList) {
            key = name;
            System.out.println("#################### EXCEL SAVE START ###################");
            System.out.println(bucket + " " + key);
            System.out.println("#################### EXCEL SAVE START ###################");

            InputStream inputStream = s3Service.getFileFromS3(key);
            excelService.processExcel(inputStream);

            System.out.println("#################### EXCEL SAVE COMPLETE ###################");
            System.out.println(bucket + " " + key);
            System.out.println("#################### EXCEL SAVE COMPLETE ###################");
        }

        return "success";
    }
}
