package calocheck.boundedContext.fooditem.service;

import calocheck.boundedContext.fooditem.dto.NutritionsDTO;
import calocheck.boundedContext.fooditem.entity.FoodItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FoodItemServiceTests {
    @Autowired
    private FoodItemService foodItemService;

    private String foodName = "음식 이름1";
    private int portionSize = 100;
    private String unit = "g";
    private double carbohydrate = 1.1;
    private double fat = 1.2;
    private double protein = 1.3;
    private double vitaminA = 1.4;
    private double vitaminB = 1.5;
    private double vitaminC = 1.6;
    private double vitaminD = 1.7;
    private double cholesterol = 1.8;
    private double sodium = 1.9;
    private double potassium = 2.0;
    private double sugar = 2.1;
    private double calcium = 2.2;
    private double iron = 2.3;

    @Test
    @DisplayName("음식 아이템 create 테스트")
    void createFoodItem() throws Exception {
        NutritionsDTO nutritionsDTO = new NutritionsDTO(
                carbohydrate, fat, protein,
                vitaminA, vitaminB, vitaminC, vitaminD,
                cholesterol, sodium, potassium,
                sugar, calcium, iron
        );

        FoodItem expected = foodItemService.create(foodName, portionSize, unit, nutritionsDTO);

        assertThat(foodItemService.findById(expected.getId())).isEqualTo(expected);
    }

    @Test
    @DisplayName("음식 아이템 update 테스트")
    void updateFoodItem() throws Exception {
        NutritionsDTO nutritionsDTO = new NutritionsDTO(
                carbohydrate, fat, protein,
                vitaminA, vitaminB, vitaminC, vitaminD,
                cholesterol, sodium, potassium,
                sugar, calcium, iron
        );

        FoodItem originItem = foodItemService.create(foodName, portionSize, unit, nutritionsDTO);

        nutritionsDTO.setCalcium(2.4);

        FoodItem updatedItem = foodItemService.update(originItem,
                originItem.getFoodName(), originItem.getPortionSize(), originItem.getUnit(),
                nutritionsDTO);

        assertThat(foodItemService.findById(originItem.getId())).isEqualTo(updatedItem);
    }

    @Test
    @DisplayName("음식 아이템 delete 테스트")
    void deleteFoodItem() throws Exception {
        NutritionsDTO nutritionsDTO = new NutritionsDTO(
                carbohydrate, fat, protein,
                vitaminA, vitaminB, vitaminC, vitaminD,
                cholesterol, sodium, potassium,
                sugar, calcium, iron
        );

        FoodItem originItem = foodItemService.create(foodName, portionSize, unit, nutritionsDTO);
        foodItemService.delete(originItem);

        assertThat(foodItemService.findById(originItem.getId())).isEqualTo(null);
    }
}
