package calocheck.boundedContext.foodCart.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import calocheck.boundedContext.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FoodCart extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;
    @OneToMany(mappedBy = "foodCart")
    private List<CartFoodInfo> cartFoodInfoList;
}
