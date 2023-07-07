package calocheck.boundedContext.mealHistory.dto;

import calocheck.boundedContext.dailyMenu.dto.DailyMenuDto;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;


@Getter
@Setter
public class MealHistoryDto {
    private Long memberId;
    private List<DailyMenuDto> dailyMenuList;
    private String mealType;
    private String mealMemo;
    private int mealScore;
    private LocalDate createDate;

    public static MealHistoryDto fromEntity(MealHistory mealHistory) {
        MealHistoryDto dto = new MealHistoryDto();
        dto.setMemberId(mealHistory.getMember().getId());
        dto.setCreateDate(LocalDate.from(mealHistory.getCreateDate()));

        List<DailyMenuDto> dailyMenuDtoList = mealHistory.getDailyMenuList().stream()
                .map(dailyMenu -> {
                    DailyMenuDto dailyMenuDto = DailyMenuDto.fromEntity(dailyMenu);
                    // Set foodName and quantity using foodInfoId
                    FoodInfo foodInfo = dailyMenu.getFoodInfo();
                    dailyMenuDto.setFoodName(foodInfo.getFoodName());
                    dailyMenuDto.setQuantity(dailyMenu.getQuantity());
                    return dailyMenuDto;
                })
                .collect(Collectors.toList());
        dto.setDailyMenuList(dailyMenuDtoList);

        dto.setMealType(mealHistory.getMealType());
        dto.setMealMemo(mealHistory.getMealMemo());
        dto.setMealScore(mealHistory.getMealScore());

        return dto;
    }
}


