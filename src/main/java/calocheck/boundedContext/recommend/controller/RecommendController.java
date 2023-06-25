package calocheck.boundedContext.recommend.controller;

import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.repository.RecommendRepository;
import calocheck.boundedContext.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;
    private final RecommendRepository recommendRepository;

    @GetMapping("/list")
    public String getRecommendList(@ModelAttribute("selectedValue") String selectedValue, Model model) {

        if(!selectedValue.equals("")){
            Recommend nutrition = recommendService.getRecommendByName(selectedValue);

            model.addAttribute("nutrition", nutrition);
        }


        return "/usr/food/recommendList";
    }

    @PostMapping("/list")
    public String postRecommendList(@RequestBody String selectedValue, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("selectedValue", selectedValue);
        return "redirect:/recommend/list";
    }

}
