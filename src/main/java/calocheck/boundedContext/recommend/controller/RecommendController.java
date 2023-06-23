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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/list")
    public String getRecommendList(@ModelAttribute("selectedValue") String selectedValue, Model model) {

        System.out.println("selectedValue222 = " + selectedValue);

        List<Recommend> allRecommendList = recommendService.getAllRecommendList();
        model.addAttribute("recommendList", allRecommendList);
        model.addAttribute("selectedValue", selectedValue);
        return "/usr/food/recommendList";
    }

    @PostMapping("/list")
    public String postRecommendList(@RequestBody String selectedValue, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("selectedValue", selectedValue);
        return "redirect:/recommend/list";
    }

}
