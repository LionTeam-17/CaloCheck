package calocheck.boundedContext.cartFoodInfo.service;

import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.repository.CartFoodInfoRepository;
import calocheck.boundedContext.foodCart.entity.FoodCart;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartFoodInfoService {
    private final CartFoodInfoRepository cartFoodInfoRepository;

    public CartFoodInfo create(FoodCart foodCart, FoodInfo foodInfo) {
        CartFoodInfo cartFoodInfo = CartFoodInfo.builder()
                .foodCart(foodCart)
                .foodInfo(foodInfo)
                .build();

        return cartFoodInfoRepository.save(cartFoodInfo);
    }

    public CartFoodInfo udpate(CartFoodInfo cartFoodInfo, FoodCart foodCart, FoodInfo foodInfo) {
        CartFoodInfo updated = cartFoodInfo.toBuilder()
                .foodCart(foodCart)
                .foodInfo(foodInfo)
                .build();

        return cartFoodInfoRepository.save(updated);
    }

    public void delete(CartFoodInfo cartFoodInfo) {
        cartFoodInfoRepository.delete(cartFoodInfo);
    }

    public CartFoodInfo findById(Long id) {
        Optional<CartFoodInfo> cartFoodInfo = cartFoodInfoRepository.findById(id);

        return cartFoodInfo.orElse(null);
    }
}
