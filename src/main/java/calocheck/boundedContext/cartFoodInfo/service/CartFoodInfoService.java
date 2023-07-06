package calocheck.boundedContext.cartFoodInfo.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.cartFoodInfo.repository.CartFoodInfoRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.nutrient.entity.Nutrient;
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

    public RsData<CartFoodInfo> addToCart(Member member, FoodInfo foodInfo, Long quantity) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (cartFoodInfo != null) {
            long updatedQuantity = cartFoodInfo.getQuantity() + quantity;
            update(cartFoodInfo, member, foodInfo, updatedQuantity);

            return RsData.of("S-2", "이미 장바구니에 있는 항목을 수정하였습니다. (%s, %d개)".formatted(foodInfo.getFoodName(), updatedQuantity));
        }

        create(member, foodInfo, quantity);

        return RsData.of("S-1", "장바구니 추가 완료(%s, %d개)".formatted(foodInfo.getFoodName(), quantity));
    }

    public RsData<CartFoodInfo> removeToCart(Member member, FoodInfo foodInfo) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (cartFoodInfo == null) {
            return RsData.of("F-1", "[%s] 식품은 장바구니에 존재하지 않습니다.".formatted(foodInfo.getFoodName()));
        }

        delete(cartFoodInfo);

        return RsData.of("S-1", "[%s] 장바구니에서 삭제되었습니다.".formatted(foodInfo.getFoodName()));
    }

    public RsData<CartFoodInfo> updateCart(Member member, FoodInfo foodInfo, Long quantity) {
        CartFoodInfo cartFoodInfo = cartFoodInfoRepository.findByMemberIdAndFoodInfoId(member.getId(), foodInfo.getId());

        if (cartFoodInfo == null) {
            return RsData.of("F-1", "[%s] 식품은 장바구니에 존재하지 않습니다.".formatted(foodInfo.getFoodName()));
        }

        update(cartFoodInfo, member, foodInfo, quantity);

        return RsData.of("S-1", "장바구니 수정 완료(%s, %d개)".formatted(foodInfo.getFoodName(), quantity));
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
                .mapToDouble(cartItem -> cartItem.getFoodInfo().getKcal() * cartItem.getQuantity())
                .sum();
    }

    public List<Nutrient> calculateTotalNutrient(List<CartFoodInfo> cartList) {
        List<Nutrient> total = new ArrayList<>() {{
            add(new Nutrient(null, "단백질", 0.0));
            add(new Nutrient(null, "지방", 0.0));
            add(new Nutrient(null, "탄수화물", 0.0));
            add(new Nutrient(null, "총당류", 0.0));
            add(new Nutrient(null, "총식이섬유", 0.0));
            add(new Nutrient(null, "칼슘", 0.0));
            add(new Nutrient(null, "철", 0.0));
            add(new Nutrient(null, "마그네슘", 0.0));
            add(new Nutrient(null, "칼륨", 0.0));
            add(new Nutrient(null, "나트륨", 0.0));
            add(new Nutrient(null, "콜레스테롤", 0.0));
            add(new Nutrient(null, "포화지방산", 0.0));
            add(new Nutrient(null, "트랜스지방산", 0.0));
            add(new Nutrient(null, "불포화지방산", 0.0));
        }};

        cartList.stream()
                .forEach(cartItem -> {
                    List<Nutrient> nutrientList = cartItem.getFoodInfo().getNutrientList();
                    LongStream.range(0, cartItem.getQuantity())
                        .forEach(i -> {
                                    IntStream.range(0, total.size()).forEach(j -> {
                                        total.get(j).addValue(nutrientList.get(j));
                                    });
                                }
                            );
                        }
                );

        return total;
    }
}
