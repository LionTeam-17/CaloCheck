package calocheck.boundedContext.cartFoodInfo.repository;


import calocheck.boundedContext.cartFoodInfo.entity.CartFoodInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartFoodInfoRepository extends JpaRepository<CartFoodInfo, Long> {
    CartFoodInfo findByMemberIdAndFoodInfoId(Long id, Long id1);
    List<CartFoodInfo> findAllByMemberId(Long id);
}
