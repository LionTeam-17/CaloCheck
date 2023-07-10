//package calocheck.boundedContext.nutrientInfo.service;
//
//import calocheck.boundedContext.nutrient.entity.Nutrient;
//import calocheck.boundedContext.nutrient.service.NutrientService;
//import calocheck.boundedContext.nutrientInfo.entity.NutrientInfo;
//import org.hibernate.Hibernate;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class NutrientInfoServiceTests {
//    @Autowired
//    private NutrientInfoService nutrientInfoService;
//    @Autowired
//    private NutrientService nutrientService;
//
//    @Test
//    @DisplayName("영양소정보 create 테스트")
//    @Transactional
//    void createNutrientInfo() throws Exception {
//        List<Nutrient> list = new ArrayList<>() {{
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//        }};
//
//        NutrientInfo nutrientInfo = nutrientInfoService.create(10, list);
//
//        assertThat(nutrientInfoService.findById(nutrientInfo.getId())).isEqualTo(nutrientInfo);
//    }
//
//    @Test
//    @DisplayName("영양소정보 update 테스트")
//    @Transactional
//    void updateNutrientInfo() throws Exception {
//        List<Nutrient> list = new ArrayList<>() {{
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//        }};
//
//        NutrientInfo nutrientInfo = nutrientInfoService.create(10, list);
//        list.set(1, nutrientService.update(list.get(1), "nutrient", 2.0, "g"));
//
//        nutrientInfo = nutrientInfoService.findById(nutrientInfo.getId());
//
//        NutrientInfo updated = nutrientInfoService.update(nutrientInfo, 10, list);
//
//        List<Nutrient> temp = updated.getNutrientList();
//
//        assertThat(temp.get(1).getValue()).isEqualTo(2.0);
//    }
//
//    @Test
//    @DisplayName("영양소정보 delete 테스트")
//    @Transactional
//    void deleteNutrientInfo() throws Exception {
//        List<Nutrient> list = new ArrayList<>() {{
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//            add(nutrientService.create("nutrient", 1.0, "g"));
//        }};
//
//        NutrientInfo nutrientInfo = nutrientInfoService.create(10, list);
//        nutrientInfoService.delete(nutrientInfo);
//
//        assertThat(nutrientInfoService.findById(nutrientInfo.getId())).isNull();
//    }
//}
