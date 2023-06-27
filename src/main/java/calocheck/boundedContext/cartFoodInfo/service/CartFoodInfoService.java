package calocheck.boundedContext.cartFoodInfo.service;

import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.repository.CartFoodInfoRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartFoodInfoService {
    private final CartFoodInfoRepository cartFoodInfoRepository;

    public CartFoodInfo addFoodInfo(Member member, FoodInfo foodInfo) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (cartFoodInfo != null) {
            return update(cartFoodInfo, member, foodInfo);
        }

        cartFoodInfo = create(member, foodInfo);

        return cartFoodInfoRepository.save(cartFoodInfo);
    }

    public boolean removeFoodInfo(Member member, FoodInfo foodInfo) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (cartFoodInfo != null) {
            cartFoodInfoRepository.delete(cartFoodInfo);
            return true;
        }

        return false;
    }

    public CartFoodInfo create(Member member, FoodInfo foodInfo) {
        CartFoodInfo cartFoodInfo = CartFoodInfo.builder()
                .member(member)
                .foodInfo(foodInfo)
                .quantity(1)
                .build();

        return cartFoodInfoRepository.save(cartFoodInfo);
    }

    public CartFoodInfo update(CartFoodInfo cartFoodInfo, Member member, FoodInfo foodInfo) {
        CartFoodInfo updated = cartFoodInfo.toBuilder()
                .member(member)
                .foodInfo(foodInfo)
                .quantity(cartFoodInfo.getQuantity() + 1)
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

    public List<CartFoodInfo> findByMember(Member member) {
        return cartFoodInfoRepository.findAllByMemberId(member.getId());
    }
}
