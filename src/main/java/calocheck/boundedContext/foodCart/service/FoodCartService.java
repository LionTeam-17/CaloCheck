package calocheck.boundedContext.foodCart.service;

import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.foodCart.entity.FoodCart;
import calocheck.boundedContext.foodCart.repository.FoodCartRepository;
import calocheck.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodCartService {
    private final FoodCartRepository foodCartRepository;

    public FoodCart create(Member member, List<CartFoodInfo> cartFoodInfoList) {
        FoodCart foodCart = FoodCart.builder()
                .member(member)
                .cartFoodInfoList(cartFoodInfoList)
                .build();

        return foodCartRepository.save(foodCart);
    }

    public FoodCart update(FoodCart foodCart, Member member, List<CartFoodInfo> cartFoodInfoList) {
        FoodCart updated = foodCart.toBuilder()
                .member(member)
                .cartFoodInfoList(cartFoodInfoList)
                .build();

        return foodCartRepository.save(updated);
    }

    public void delete(FoodCart foodCart) {
        foodCartRepository.delete(foodCart);
    }

    public FoodCart findById(Long id) {
        Optional<FoodCart> foodCart = foodCartRepository.findById(id);

        return foodCart.orElse(null);
    }
}
