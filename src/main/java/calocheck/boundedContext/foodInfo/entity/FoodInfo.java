package calocheck.boundedContext.foodInfo.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FoodInfo extends BaseEntity {
    private String foodCode;
    private String foodName; // 식품명
    private String manufacturer; // 제조사/유통사
    private String category; // 식품대분류
    private int portionSize; // 1회 제공량
    private String unit; // 내용량 단위
    private int totalSize; // 총 내용량
    private double kcal; // 에너지(kcal)
    @OneToMany(mappedBy = "foodInfo", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Nutrient> nutrientList = new ArrayList<>();

    public void addNutrient(Nutrient nutrient) {
        nutrientList.add(nutrient);
    }
}

// 5 식품명
// 7 제조사/유통사
// 8 식품군대분류
// 10 1회제공량
// 11 내용량_단위
// 12 총내용량(g)
// 13 총내용량(mL)
// 14 에너지(kcal)
// 16 단백질(g)
// 17 지방(g)
// 18 탄수화물(g)
// 19 총당류(g)
// 24 총식이섬유(g)
// 25 칼슘(mg)
// 26 철(mg)
// 27 마그네슘(mg)
// 29 칼륨(mg)
// 30 나트륨(mg)
// 73 콜레스테롤(g)
// 75 포화지방산(g)
// 84 트랜스지방산(g)
// 85 불포화지방산(g)