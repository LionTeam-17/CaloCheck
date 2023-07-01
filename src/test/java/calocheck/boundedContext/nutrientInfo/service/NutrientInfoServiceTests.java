//package calocheck.boundedContext.nutrientInfo.service;
//
//import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//public class NutrientInfoServiceTests {
//    @Autowired
//    private NutrientInfoService nutrientInfoService;
//
//    private final int SIZE = 18;
//    @Test
//    @DisplayName("영양소정보 create 테스트")
//    void createnutrientInfo() throws Exception {
//        double[] data = new double[SIZE];
//        for (int i = 1; i <= SIZE; i++) {
//            data[i - 1] = i * 0.1;
//        }
//
//        NutrientInfo nutrientInfo = nutrientInfoService.create(10,
//                data[0], data[1], data[2],
//                data[3], data[4], data[5],
//                data[6], data[7], data[8], data[9],
//                data[10], data[11], data[12], data[13]);
//
//        assertThat(nutrientInfoService.findById(nutrientInfo.getId())).isEqualTo(nutrientInfo);
//    }
//
//    @Test
//    @DisplayName("영양소정보 update 테스트")
//    void updatenutrientInfo() throws Exception {
//        double[] data = new double[SIZE];
//        for (int i = 1; i <= SIZE; i++) {
//            data[i - 1] = i * 0.1;
//        }
//
//        NutrientInfo nutrientInfo = nutrientInfoService.create(10,
//                data[0], data[1], data[2],
//                data[3], data[4], data[5],
//                data[6], data[7], data[8], data[9],
//                data[10], data[11], data[12], data[13]);
//
//        NutrientInfo updated = nutrientInfoService.update(nutrientInfo, 10,
//                data[0], data[1], data[2],
//                data[3], data[4], data[5],
//                data[6], data[7], data[8], data[9],
//                data[10] + 1, data[11], data[12], data[13]);
//
//        assertThat(nutrientInfoService.findById(updated.getId()).getCholesterol()).isEqualTo(data[10] + 1);
//    }
//
//    @Test
//    @DisplayName("영양소정보 delete 테스트")
//    void deletenutrientInfo() throws Exception {
//        double[] data = new double[SIZE];
//        for (int i = 1; i <= SIZE; i++) {
//            data[i - 1] = i * 0.1;
//        }
//
//        NutrientInfo nutrientInfo = nutrientInfoService.create(10,
//                data[0], data[1], data[2],
//                data[3], data[4], data[5],
//                data[6], data[7], data[8], data[9],
//                data[10], data[11], data[12], data[13]);
//
//        nutrientInfoService.delete(nutrientInfo);
//
//        assertThat(nutrientInfoService.findById(nutrientInfo.getId())).isNull();
//    }
//}