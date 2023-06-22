package calocheck.boundedContext.fooditem.service;

import calocheck.boundedContext.fooditem.entity.NutritionInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class NutritionInfoServiceTests {
    @Autowired
    private NutritionInfoService nutritionInfoService;

    private final int SIZE = 18;
    @Test
    @DisplayName("영양소정보 create 테스트")
    void createNutritionInfo() throws Exception {
        double[] data = new double[SIZE];
        for (int i = 1; i <= SIZE; i++) {
            data[i - 1] = i * 0.1;
        }

        NutritionInfo nutritionInfo = nutritionInfoService.create(10,
                data[0], data[1], data[2],
                data[3], data[4], data[5],
                data[6], data[7], data[8], data[9],
                data[10], data[11], data[12], data[13],
                data[14], data[15], data[16], data[17]);

        assertThat(nutritionInfoService.findById(nutritionInfo.getId())).isEqualTo(nutritionInfo);
    }

    @Test
    @DisplayName("영양소정보 update 테스트")
    void updateNutritionInfo() throws Exception {
        double[] data = new double[SIZE];
        for (int i = 1; i <= SIZE; i++) {
            data[i - 1] = i * 0.1;
        }

        NutritionInfo nutritionInfo = nutritionInfoService.create(10,
                data[0], data[1], data[2],
                data[3], data[4], data[5],
                data[6], data[7], data[8], data[9],
                data[10], data[11], data[12], data[13],
                data[14], data[15], data[16], data[17]);

        NutritionInfo updated = nutritionInfoService.update(nutritionInfo, 10,
                data[0], data[1], data[2],
                data[3], data[4], data[5],
                data[6], data[7], data[8], data[9],
                data[10] + 1, data[11], data[12], data[13],
                data[14], data[15], data[16], data[17]);

        assertThat(nutritionInfoService.findById(updated.getId()).getVitaminA()).isEqualTo(data[10] + 1);
    }

    @Test
    @DisplayName("영양소정보 delete 테스트")
    void deleteNutritionInfo() throws Exception {
        double[] data = new double[SIZE];
        for (int i = 1; i <= SIZE; i++) {
            data[i - 1] = i * 0.1;
        }

        NutritionInfo nutritionInfo = nutritionInfoService.create(10,
                data[0], data[1], data[2],
                data[3], data[4], data[5],
                data[6], data[7], data[8], data[9],
                data[10], data[11], data[12], data[13],
                data[14], data[15], data[16], data[17]);

        nutritionInfoService.delete(nutritionInfo);

        assertThat(nutritionInfoService.findById(nutritionInfo.getId())).isNull();
    }
}