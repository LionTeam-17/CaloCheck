package calocheck.boundedContext.cartFoodInfo.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.cartFoodInfo.dto.CartDTO;
import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartFoodInfo")
public class CartFoodInfoController {
    private final CartFoodInfoService cartFoodInfoService;
    private final FoodInfoService foodInfoService;
    private final Rq rq;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showCartList(Model model) {
        Member member = rq.getMember();

        List<CartFoodInfo> cartList = cartFoodInfoService.findByMember(member);
        List<FoodInfo> foodInfoList = cartList.stream()
                        .map(element -> element.getFoodInfo())
                        .collect(Collectors.toList());

        model.addAttribute("cartList", foodInfoList);

        return "usr/cartFoodInfo/cartList";
    }

    @PostMapping("/addFoodInfo")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CartDTO addCartFoodInfo(@RequestParam("foodId") Long foodId) {
        Member member = rq.getMember();
        FoodInfo foodInfo = foodInfoService.findById(foodId);

        cartFoodInfoService.addFoodInfo(member, foodInfo);
        return new CartDTO("success");
    }

    @PostMapping("/removeFoodInfo")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CartDTO removeCartFoodInfo(@RequestParam("foodId") Long foodId) {
        Member member = rq.getMember();
        FoodInfo foodInfo = foodInfoService.findById(foodId);

        cartFoodInfoService.removeFoodInfo(member, foodInfo);
        return new CartDTO("success");
    }
}
