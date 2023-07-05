package calocheck.boundedContext.dailyMenu.service;

import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.repository.DailyMenuRepository;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

// MealHistory (DailyMenu, DailyMenu, DailyMenu)
@Service
@RequiredArgsConstructor
public class DailyMenuService {

    private final DailyMenuRepository dailyMenuRepository;
    private final CartFoodInfoService cartFoodInfoService;

    @Transactional
    public DailyMenu create(Member member, FoodInfo foodInfo, Long quantity) {
        DailyMenu dailyMenu = DailyMenu.builder()
                .member(member)
                .foodInfo(foodInfo)
                .quantity(quantity)
                .build();

        return dailyMenuRepository.save(dailyMenu);
    }

    @Transactional
    public List<DailyMenu> create(Member member, List<CartFoodInfo> cartList) {
        List<DailyMenu> dailyMenuList = new ArrayList<>();

        cartList.stream()
                .forEach(
                        cartFoodInfo -> dailyMenuList.add(
                                create(
                                        member,
                                        cartFoodInfo.getFoodInfo(),
                                        cartFoodInfo.getQuantity()
                                )
                        )
                );

        return dailyMenuList;
    }

    @Transactional
    public void delete(DailyMenu dailyMenu) {
        dailyMenuRepository.delete(dailyMenu);
    }

    public DailyMenu findById(Long id) {
        Optional<DailyMenu> dailyMenu = dailyMenuRepository.findById(id);

        return dailyMenu.orElse(null);
    }

    @Transactional
    public void updateMealHistory(DailyMenu dailyMenu){

        dailyMenuRepository.save(dailyMenu);
    }

}
