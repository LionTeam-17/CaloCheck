package calocheck.boundedContext.recommend.controller;

import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/list")
    public String getRecommendList(Model model) {

        List<Recommend> allRecommendList = recommendService.getAllRecommendList();

        model.addAttribute("recommendList", allRecommendList);

        return "/usr/food/recommendList";
    }


    @PostMapping("/list")
    @ResponseBody
    public String getRecommendList(@RequestBody String selectedValue){

        System.out.println("selectedValue = " + selectedValue);

        return "선택된 영양소 : " + selectedValue;
    }

}
