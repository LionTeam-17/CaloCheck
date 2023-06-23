package calocheck.boundedContext.recommend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    @GetMapping("/list")
    public String getRecommendList(){

        return "/usr/food/recommendList";
    }


}
