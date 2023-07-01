package calocheck.boundedContext.mealHistory.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.entity.Nutrient;
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
public class MealHistory extends BaseEntity {

    //회원의 전체 식사 기록을 의미하는 MealHistory 엔티티

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mealHistory")
    private List<DailyMenu> dailyMenuList;

    private String mealType;

    private String mealMemo;

    private int mealScore;
}