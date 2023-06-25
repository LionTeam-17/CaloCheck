package calocheck.boundedContext.recommend.controller;

import calocheck.boundedContext.recommend.entity.Recommend;
import calocheck.boundedContext.recommend.repository.RecommendRepository;
import calocheck.boundedContext.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


        if (!selectedValue.isEmpty()) {
            Recommend nutrition = recommendService.getRecommendByName(selectedValue);

            //네트워크 측에서 모델에 들어간 nutrition 은 잘 받았지만, 화면에 내용이 출력되지 않고 있는 상황.
            model.addAttribute("nutrition", nutrition);
        }

        return "/usr/food/recommendList";
    }

    @PostMapping("/list")
    public String postRecommendList(@RequestBody String selectedValue, RedirectAttributes redirectAttributes) {

        //redirect 를 하고, get 요청이 이루어 졌을 때 내용을 사용할 수 있도록 함.
        redirectAttributes.addFlashAttribute("selectedValue", selectedValue);
        return "redirect:/recommend/list";
    }

}
