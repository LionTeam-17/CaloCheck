package calocheck.boundedContext.cartFoodInfo.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.repository.CartFoodInfoRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.service.NutrientService;
import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;
import calocheck.boundedContext.nutrientInfo.service.NutrientInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
public class CartFoodInfoService {
    private final CartFoodInfoRepository cartFoodInfoRepository;
    private final NutrientService nutrientService;
    private final NutrientInfoService nutrientInfoService;

    public RsData<CartFoodInfo> addFoodInfo(Member member, FoodInfo foodInfo, Long quantity) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (cartFoodInfo != null) {
            long updatedQuantity = cartFoodInfo.getQuantity() + quantity;
            update(cartFoodInfo, member, foodInfo, updatedQuantity);
            return RsData.of("success", "이미 장바구니에 있는 항목을 수정(%d개)하였습니다.".formatted(updatedQuantity));
        }

        cartFoodInfo = create(member, foodInfo, quantity);
        cartFoodInfoRepository.save(cartFoodInfo);

        return RsData.of("success", "장바구니 추가 완료");
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

    public CartFoodInfo create(Member member, FoodInfo foodInfo, Long quantity) {
        CartFoodInfo cartFoodInfo = CartFoodInfo.builder()
                .member(member)
                .foodInfo(foodInfo)
                .quantity(quantity)
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

    public void delete(CartFoodInfo cartFoodInfo) {
        cartFoodInfoRepository.delete(cartFoodInfo);
    }

    public void deleteAllList(Member member){

        List<CartFoodInfo> allByMember = findAllByMember(member);

        for (CartFoodInfo cartFoodInfo : allByMember) {
            delete(cartFoodInfo);
        }

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
                .mapToDouble(cartItem -> cartItem.getFoodInfo().getNutrientInfo().getKcal() * cartItem.getQuantity())
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
                .forEach(cartItem -> {
                    NutrientInfo nutrientInfo = cartItem.getFoodInfo().getNutrientInfo();
                    LongStream.range(0, cartItem.getQuantity())
                            .forEach(i -> nutrientInfoService.addNutrientData(
                                        res, nutrientInfo.getNutrientList()
                                    )
                            );
                    }
                );

        return res;
    }
}
