package calocheck.boundedContext.dailyMenu.dto;

import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyMenuDto {
    private String foodName;

    public static DailyMenuDto fromEntity(DailyMenu dailyMenu) {
        DailyMenuDto dto = new DailyMenuDto();
        dto.setFoodName(dailyMenu.getFoodInfo().getFoodName());

        return dto;
    }
}
