package calocheck.boundedContext.cartFoodInfo.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.cartFoodInfo.dto.CartDTO;
import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.dailyFoodInfo.service.DailyFoodInfoService;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.service.DailyMenuService;
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
    private final DailyMenuService dailyMenuService;
    private final DailyFoodInfoService dailyFoodInfoService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showCartList(Model model) {
        Member member = rq.getMember();

        List<CartFoodInfo> cartList = cartFoodInfoService.findByMember(member);

        model.addAttribute("cartList", cartList);

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

    @PostMapping("/updateFoodInfo")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CartDTO updateCartFoodInfo(@RequestParam("foodId") Long foodId, @RequestParam("quantity") Long quantity) {
        Member member = rq.getMember();
        FoodInfo foodInfo = foodInfoService.findById(foodId);

        if (quantity == 0) {
            cartFoodInfoService.removeFoodInfo(member, foodInfo);
            return new CartDTO("success");
        }

        cartFoodInfoService.updateFoodInfo(member, foodInfo, quantity);
        return new CartDTO("success");
    }

    @GetMapping("/addMenu")
    @PreAuthorize("isAuthenticated()")
    public String showAddMenu(Model model){
        Member member = rq.getMember();

        return "usr/cartFoodInfo/addMenu";
    }

    @PostMapping("/addMenu")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public String addMenu(String mealTime, int menuScore, String menuMemo) {

        //DailyMenu 는 member 를 가지고 있고, 데일리 메뉴에는 DailyFoodInfo(음식 개별당 영양)의 리스트가 들어있다.

        Member loginMember = rq.getMember();

        DailyMenu dailyMenuByMember = dailyMenuService.getByMember(loginMember);

        //DailyMenu 에 DailyFoodInfo들(리스트) 을 넣어주어야 한다??
//        dailyFoodInfoService.create(dailyMenuByMember, )

        //장바구니에서 삭제
//        cartFoodInfoService.delete();

        return "%s, %d, %s".formatted(mealTime, menuScore, menuMemo);
    }
}
