package calocheck.boundedContext.mealHistory.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MealHistory {

    //회원의 전체 식사 기록을 의미하는 MealHistory 엔티티

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mealHistory")
    private List<DailyMenu> dailyMenuList;

    private String mealType;

    private String mealMemo;

    private int mealScore;

    @ElementCollection
    private List<Nutrient> nutrients;

    @PrePersist
    protected void onCreate() {
        createDate = LocalDate.now();
    }

}