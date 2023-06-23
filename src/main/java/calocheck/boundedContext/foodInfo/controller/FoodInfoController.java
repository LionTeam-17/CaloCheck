package calocheck.boundedContext.foodInfo.controller;

import calocheck.base.util.FoodDataExtractor;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foodInfo")
@RequiredArgsConstructor
public class FoodInfoController {
    private final FoodInfoService foodInfoService;
    private final FoodDataExtractor temp;

    @GetMapping("/excel-data-save")
    public String test() {
        try {
            temp.readFile();
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }
}
