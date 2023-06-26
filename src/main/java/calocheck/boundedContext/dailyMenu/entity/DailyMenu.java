package calocheck.boundedContext.dailyMenu.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.dailyFoodInfo.entity.DailyFoodInfo;
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
public class DailyMenu extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @OneToMany(mappedBy = "dailyMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    List<DailyFoodInfo> dailyFoodInfoList;
}