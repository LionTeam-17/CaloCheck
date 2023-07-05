package calocheck.boundedContext.mealHistory.dto;

import calocheck.boundedContext.dailyMenu.dto.DailyMenuDto;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MealHistoryDto {
    private Long memberId;
    private List<DailyMenuDto> dailyMenuList;
    private String mealType;
    private String mealMemo;
    private int mealScore;

    public static MealHistoryDto fromEntity(MealHistory mealHistory) {
        MealHistoryDto dto = new MealHistoryDto();
        dto.setMemberId(mealHistory.getMember().getId());

        List<DailyMenuDto> dailyMenuDtoList = mealHistory.getDailyMenuList().stream()
                .map(DailyMenuDto::fromEntity)
                .collect(Collectors.toList());
        dto.setDailyMenuList(dailyMenuDtoList);

        dto.setMealType(mealHistory.getMealType());
        dto.setMealMemo(mealHistory.getMealMemo());
        dto.setMealScore(mealHistory.getMealScore());

        return dto;
    }
}

