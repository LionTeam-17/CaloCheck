package calocheck.boundedContext.tag.service;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.service.NutrientService;
import calocheck.boundedContext.tag.entity.Tag;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@Transactional
class TagServiceTest {

    @Autowired
    private TagService tagService;
    @Autowired
    private FoodInfoService foodInfoService;
    @Autowired
    private NutrientService nutrientService;

    @BeforeEach
    void setUp() {
        List<Nutrient> nutrientList = new ArrayList<>();

        FoodInfo testFood = foodInfoService.create("testFood", "제조사", "카테고리",
                1, "g", 200, 1000, nutrientList);

        Nutrient testNutrient = nutrientService.create(testFood, "testNutrient", 30.0);

        nutrientList.add(testNutrient);

        //testFood는 영양소로 testNutrient를 30 만큼 가짐
        FoodInfo updatedTestFood = foodInfoService.update(testFood, testFood.getFoodName(), testFood.getManufacturer(),
                testFood.getCategory(), testFood.getPortionSize(), testFood.getUnit(), testFood.getTotalSize(),
                testFood.getKcal(), nutrientList);
    }

    @Test
    @DisplayName("태그 기준치보다 높다면 태그 반영")
    void t001() {

        FoodInfo testFood = foodInfoService.findByFoodName("testFood");

        //20을 기준치로 하는 testNutrient 생성
        tagService.createTag("testNutrient", "FFFFFF", 20);

        List<Tag> tagList = tagService.getTagList(testFood);

        assertThat(tagList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("태그 기준치보다 낮다면 태그가 반영되지 않음")
    void t002() {

        FoodInfo testFood = foodInfoService.findByFoodName("testFood");

        //20을 기준치로 하는 testNutrient 생성
        tagService.createTag("testNutrient", "FFFFFF", 50);

        List<Tag> tagList = tagService.getTagList(testFood);

        assertThat(tagList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("태그명과 영양소 이름이 다르다면 검출되지 않음")
    void t003() {

        FoodInfo testFood = foodInfoService.findByFoodName("testFood");

        //20을 기준치로 하는 testNutrient 생성
        tagService.createTag("anotherNutrient", "FFFFFF", 50);

        List<Tag> tagList = tagService.getTagList(testFood);

        assertThat(tagList.size()).isEqualTo(0);
    }

}