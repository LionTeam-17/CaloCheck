package calocheck.boundedContext.cartFoodInfo.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.cartFoodInfo.dto.CartDTO;
import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.service.CriteriaService;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.service.DailyMenuService;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.service.MealHistoryService;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.dto.NutrientDTO;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cartFoodInfo")
public class CartFoodInfoController {
    private final Rq rq;
    private final CartFoodInfoService cartFoodInfoService;
    private final FoodInfoService foodInfoService;
    private final MealHistoryService mealHistoryService;
    private final DailyMenuService dailyMenuService;
    private final CriteriaService criteriaService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showCartList(Model model) {
        Member member = rq.getMember();

        List<CartFoodInfo> cartList = cartFoodInfoService.findAllByMember(member);

        model.addAttribute("cartList", cartList);

        return "usr/cartFoodInfo/list";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CartDTO addCartFoodInfo(@RequestParam("foodId") Long foodId, @RequestParam("quantity") Long quantity) {
        Member member = rq.getMember();
        FoodInfo foodInfo = foodInfoService.findById(foodId);

        if (foodInfo == null) {
            return new CartDTO("fail", "해당 식품이 존재하지 않습니다.");
        }
        else if(quantity < 1) {
            return new CartDTO("fail", "요청하신 수량은 올바르지 않습니다.");
        }

        RsData<CartFoodInfo> res = cartFoodInfoService.addToCart(member, foodInfo, quantity);

        return new CartDTO("success", res.getMsg());
    }

    @PostMapping("/remove")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CartDTO removeCartFoodInfo(@RequestParam("foodId") Long foodId) {
        Member member = rq.getMember();
        FoodInfo foodInfo = foodInfoService.findById(foodId);

        if (foodInfo == null) {
            return new CartDTO("fail", "해당 식품이 존재하지 않습니다.");
        }

        RsData<CartFoodInfo> res = cartFoodInfoService.removeToCart(member, foodInfo);

        if (res.isFail()) {
            return new CartDTO("fail", res.getMsg());
        }

        return new CartDTO("success", res.getMsg(), foodId);
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public CartDTO updateCartFoodInfo(@RequestParam("foodId") Long foodId, @RequestParam("quantity") Long quantity) {
        Member member = rq.getMember();
        FoodInfo foodInfo = foodInfoService.findById(foodId);

        if (foodInfo == null) {
            return new CartDTO("fail", "해당 식품이 존재하지 않습니다.");
        }
        else if(quantity < 0) {
            return new CartDTO("fail", "요청하신 수량은 올바르지 않습니다.");
        }

        if (quantity == 0) {
            RsData<CartFoodInfo> delRes = cartFoodInfoService.removeToCart(member, foodInfo);

            if (delRes.isFail()) {
                return new CartDTO("fail", delRes.getMsg());
            }

            return new CartDTO("success", delRes.getMsg(), foodId);
        }

        RsData<CartFoodInfo> updateRes = cartFoodInfoService.updateCart(member, foodInfo, quantity);

        return new CartDTO("success", updateRes.getMsg());
    }


    @GetMapping("/total")
    @PreAuthorize("isAuthenticated()")
    public String showCartTotal(Model model) {
        Member member = rq.getMember();
        List<CartFoodInfo> cartList = cartFoodInfoService.findAllByMember(member);

        if (cartList == null) {
            return rq.historyBack("잘못된 접근입니다");
        }

        List<NutrientDTO> nutrientTotal = cartFoodInfoService.calcTotalNutrient(cartList);
        Double kcalTotal = cartFoodInfoService.calculateTotalKcal(cartList);

        model.addAttribute("kcalTotal", kcalTotal);
        model.addAttribute("nutrientTotal", nutrientTotal);

        return "usr/cartFoodInfo/total";
    }

    @GetMapping("/addMenu")
    @PreAuthorize("isAuthenticated()")
    public String showAddMenu(Model model) {
        Member member = rq.getMember();

        List<CartFoodInfo> cartList = cartFoodInfoService.findAllByMember(member);      //카트에 담겨있는 리스트

        if (cartList == null) {
            return rq.historyBack("잘못된 접근입니다");
        }

        Criteria myCriteria = criteriaService.findByGenderAndAge(member);           //나의 권장량
        List<MealHistory> todayMealHistory = mealHistoryService.findByMemberAndCreateDate(member); //오늘 먹은 내용들
        List<NutrientDTO> nutrientTotal = cartFoodInfoService.calcTotalNutrient(cartList);    //카트 내용의 영양소 총합

        //이걸 먹게되면 영양소가 어떻게 되는가?
        List<NutrientDTO> calcNutrients = mealHistoryService.calcNutrient(myCriteria, todayMealHistory, nutrientTotal);

        model.addAttribute("calcNutrients", calcNutrients);
        model.addAttribute("cartList", cartList);
        
        return "usr/cartFoodInfo/addMenu";
    }

    @PostMapping("/addMenu")
    @PreAuthorize("isAuthenticated()")
    public String addMenu(@RequestParam("mealType") String mealType,
                          @RequestParam("menuMemo") String menuMemo,
                          @RequestParam("menuScore") int menuScore) {
        Member member = rq.getMember();

        List<CartFoodInfo> cartList = cartFoodInfoService.findAllByMember(member);
        List<DailyMenu> dailyMenuList = dailyMenuService.create(member, cartList);

        MealHistory mealHistory = mealHistoryService.create(member, dailyMenuList, mealType, menuMemo, menuScore);

        //장바구니 삭제
        cartFoodInfoService.deleteAllList(member);

        //내 식단 캘린더로 이동
        return "redirect:/mealHistory/" + member.getId();    }
}
