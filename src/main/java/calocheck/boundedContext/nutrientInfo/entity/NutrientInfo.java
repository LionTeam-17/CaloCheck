package calocheck.boundedContext.nutrientInfo.entity;

import calocheck.base.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NutrientInfo extends BaseEntity {
    private double kcal; // 에너지(kcal)
    private double protein; // 단백질
    private double fat; // 지방
    private double carbohydrate; // 탄수화물
    private double sugar; // 당류
    private double fiber; // 식이섬유
    private double calcium; // 칼슘
    private double iron; // 철
    private double magnesium; // 마그네슘
    private double potassium; // 칼륨
    private double sodium; // 나트륨
    private double cholesterol; // 콜레스테롤
    private double saturated; // 포화 지방산
    private double transFattyAcid; // 트랜스 지방산
    private double unSaturated; // 불포화 지방산
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