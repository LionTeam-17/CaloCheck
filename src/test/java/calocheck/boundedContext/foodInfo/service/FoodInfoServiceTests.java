package calocheck.boundedContext.foodInfo.service;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.nutritionInfo.entity.NutritionInfo;
import calocheck.boundedContext.nutritionInfo.service.NutritionInfoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FoodInfoServiceTests {
    @Autowired
    private NutritionInfoService nutritionInfoService;
    @Autowired
    private FoodInfoService foodInfoService;

    private final int SIZE = 14;
    private String foodName = "음식 이름1";
    private String manufacturer = "제조사1";
    private String category = "분류1";
    private int portionSize = 100;
    private String unit = "g";
    private int totalSize = 500;

    @Test
    @DisplayName("음식정보 create 테스트")
    void createFoodInfo() throws Exception {
        double[] data = new double[SIZE];
        for (int i = 1; i <= SIZE; i++) {
            data[i - 1] = i * 0.1;
        }
        NutritionInfo nutritionInfo = nutritionInfoService.create(10,
                data[0], data[1], data[2],
                data[3], data[4], data[5],
                data[6], data[7], data[8], data[9],
                data[10], data[11], data[12], data[13]);

        FoodInfo foodInfo = foodInfoService.create(nutritionInfo,
                foodName, manufacturer, category,
                portionSize, unit, totalSize);

        assertThat(foodInfoService.findById(foodInfo.getId())).isEqualTo(foodInfo);
    }

    @Test
    @DisplayName("음식정보 update 테스트")
    void updateFoodInfo() throws Exception {
        double[] data = new double[SIZE];
        for (int i = 1; i <= SIZE; i++) {
            data[i - 1] = i * 0.1;
        }
        NutritionInfo nutritionInfo = nutritionInfoService.create(10,
                data[0], data[1], data[2],
                data[3], data[4], data[5],
                data[6], data[7], data[8], data[9],
                data[10], data[11], data[12], data[13]);

        FoodInfo foodInfo = foodInfoService.create(nutritionInfo,
                foodName, manufacturer, category,
                portionSize, unit, totalSize);

        FoodInfo updated = foodInfoService.update(foodInfo, nutritionInfo,
                foodName, manufacturer, category,
                portionSize, unit, totalSize + 100);

        assertThat(foodInfoService.findById(updated.getId()).getTotalSize()).isEqualTo(totalSize + 100);
    }

    @Test
    @DisplayName("음식정보 delete 테스트")
    void deleteFoodInfo() throws Exception {
        double[] data = new double[SIZE];
        for (int i = 1; i <= SIZE; i++) {
            data[i - 1] = i * 0.1;
        }
        NutritionInfo nutritionInfo = nutritionInfoService.create(10,
                data[0], data[1], data[2],
                data[3], data[4], data[5],
                data[6], data[7], data[8], data[9],
                data[10], data[11], data[12], data[13]);

        FoodInfo foodInfo = foodInfoService.create(nutritionInfo,
                foodName, manufacturer, category,
                portionSize, unit, totalSize);

        foodInfoService.delete(foodInfo);

        assertThat(foodInfoService.findById(foodInfo.getId())).isNull();
    }

    @Test
    @DisplayName("음식정보 findAll")
    void findAllFoodInfo() {
        List<FoodInfo> foodInfoList = foodInfoService.findAll();

        assertThat(foodInfoList.size()).isEqualTo(99);
    }

    @Test
    @DisplayName("FoodInfo findAll 페이징")
    void findAllFoodInfoWithPaging() {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<FoodInfo> paging = foodInfoService.findAll(pageable);
        List<FoodInfo> foodInfoList = paging.getContent();

        assertThat(foodInfoList.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("FoodName 키워드 검색")
    void findByFoodNameContains() {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<FoodInfo> paging = foodInfoService.findByFoodNameContains(pageable, "강정");
        List<FoodInfo> foodInfoList = paging.getContent();

        assertThat(foodInfoList.size()).isEqualTo(2);
    }
}
