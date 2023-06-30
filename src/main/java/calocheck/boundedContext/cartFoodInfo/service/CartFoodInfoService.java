package calocheck.boundedContext.cartFoodInfo.service;

import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.repository.CartFoodInfoRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.service.NutrientService;
import calocheck.boundedContext.nutrientInfo.service.NutrientInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartFoodInfoService {
    private final CartFoodInfoRepository cartFoodInfoRepository;
    private final NutrientService nutrientService;
    private final NutrientInfoService nutrientInfoService;

    public CartFoodInfo addFoodInfo(Member member, FoodInfo foodInfo) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (cartFoodInfo != null) {
            long quantity = cartFoodInfo.getQuantity() + 1;
            return update(cartFoodInfo, member, foodInfo, quantity);
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

    public boolean updateFoodInfo(Member member, FoodInfo foodInfo, Long quantity) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (quantity == 0) {

        }

        if (cartFoodInfo != null) {
            CartFoodInfo updated = cartFoodInfo.toBuilder()
                    .quantity(quantity)
                    .build();

            cartFoodInfoRepository.save(updated);
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

    private CartFoodInfo update(CartFoodInfo cartFoodInfo, Member member, FoodInfo foodInfo, Long quantity) {
        CartFoodInfo updated = cartFoodInfo.toBuilder()
                .member(member)
                .foodInfo(foodInfo)
                .quantity(quantity)
                .build();

        return cartFoodInfoRepository.save(updated);
    }

    private void delete(CartFoodInfo cartFoodInfo) {
        cartFoodInfoRepository.delete(cartFoodInfo);
    }

    public CartFoodInfo findById(Long id) {
        Optional<CartFoodInfo> cartFoodInfo = cartFoodInfoRepository.findById(id);

        return cartFoodInfo.orElse(null);
    }

    public List<CartFoodInfo> findAllByMember(Long id) {
        return cartFoodInfoRepository.findAllByMemberId(id);
    }

    public List<CartFoodInfo> findAllByMember(Member member) {
        return cartFoodInfoRepository.findAllByMemberId(member.getId());
    }

    public Double calculateTotalKcal(List<CartFoodInfo> cartList) {
        return cartList.stream()
                .map(cartItem -> cartItem.getFoodInfo().getNutrientInfo().getKcal())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public List<Nutrient> calculateTotalNutrient(List<CartFoodInfo> cartList) {
        List<Nutrient> res = new ArrayList<>() {{
            add(new Nutrient("단백질", 0.0, "g"));
            add(new Nutrient("지방", 0.0, "g"));
            add(new Nutrient("탄수화물", 0.0, "g"));
            add(new Nutrient("총당류", 0.0, "g"));
            add(new Nutrient("총식이섬유", 0.0, "g"));
            add(new Nutrient("칼슘", 0.0, "mg"));
            add(new Nutrient("철", 0.0, "mg"));
            add(new Nutrient("마그네슘", 0.0, "mg"));
            add(new Nutrient("칼륨", 0.0, "mg"));
            add(new Nutrient("나트륨", 0.0, "mg"));
            add(new Nutrient("콜레스테롤", 0.0, "g"));
            add(new Nutrient("포화지방산", 0.0, "g"));
            add(new Nutrient("트랜스지방산", 0.0, "g"));
            add(new Nutrient("불포화지방산", 0.0, "g"));
        }};

        cartList.stream()
                .map(cartItem -> cartItem.getFoodInfo().getNutrientInfo())
                .forEach(nutrient -> nutrientInfoService.addNutrientData(res, nutrient.getNutrientList()));

        return res;
    }
}
