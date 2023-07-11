package calocheck.boundedContext.criteria.service;

import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.service.DailyMenuService;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.repository.FoodInfoRepository;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.repository.MemberRepository;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.service.NutrientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@Transactional
class CriteriaServiceTest {

    @Autowired
    private CriteriaService criteriaService;
    @Autowired
    private DailyMenuService dailyMenuService;
    @Autowired
    private NutrientService nutrientService;
    @Autowired
    private FoodInfoService foodInfoService;
    @Autowired
    private MemberService memberService;

    private Member testMan;
    private Member testWoman;
    private FoodInfo testFood1;
    private FoodInfo testFood2;

    @BeforeEach
    void setUp() {

        //testMan은 30세 남성으로, 180의 키와 70의 몸무게를 가진다고 가정
        testMan = Member.builder()
                .age(30)
                .gender("M")
                .height(180.0)
                .weight(70.0)
                .build();

        memberService.save(testMan);

        //testWoman은 25세 여성으로, 160의 키와 45의 몸무게를 가진다고 가정
        testWoman = Member.builder()
                .age(25)
                .gender("W")
                .height(160.0)
                .weight(45.0)
                .build();

        memberService.save(testWoman);

        testFood1 = FoodInfo.builder()
                .foodCode("TEST001")
                .foodName("testFood1")
                .build();

        foodInfoService.save(testFood1);

        testFood2 = FoodInfo.builder()
                .foodCode("TEST002")
                .foodName("testFood2")
                .build();

        foodInfoService.save(testFood2);

        //testFood1은 영양소로 20의 단백질과 15의 지방을 가짐
        Nutrient testProtein1 = nutrientService.create(testFood1, "단백질", 20.0);
        Nutrient testFat1 = nutrientService.create(testFood1, "지방", 15.0);

        List<Nutrient> testNutList1 = Arrays.asList(testProtein1, testFat1);

        testFood1 = FoodInfo.builder()
                .nutrientList(testNutList1)
                .build();

        //testFood2는 영양소로 0.1의 칼슘과 0.2의 나트륨을 가짐
        Nutrient testCalcium2 = nutrientService.create(testFood2, "칼슘", 0.1);
        Nutrient testSodium2 = nutrientService.create(testFood2, "나트륨", 0.2);

        List<Nutrient> testNutList2 = Arrays.asList(testCalcium2, testSodium2);

        testFood2 = FoodInfo.builder()
                .nutrientList(testNutList2)
                .build();
    }

    /*
    4. 오늘 먹은 영양소 계산 가능
    5. (나의 권장량 - 오늘 먹은 영양소 - 지금 카트에 담긴 음식) 계산이 가능.
     */

    @Test
    @DisplayName("실행시 권장량이 엑셀 파일을 통해 자동으로 생성되며, 성별과 나이를 통해 자신의 권장량을 확인할 수 있다.")
    void t001(){

        Criteria criteriaForTestMan = criteriaService.findByGenderAndAge(testMan);
        Criteria criteriaForTestWoman = criteriaService.findByGenderAndAge(testWoman);

        //30세 남성의 단백질 권장량은 65g 이고, 권장 칼슘 양은 0.8g 이다.
        //assertThat(criteriaForTestMan.getProtein()).isEqualTo(65);
        assertThat(criteriaForTestMan.getCalcium()).isEqualTo(0.8);

        //25세 여성의 단백질 권장량은 55g 이고, 권장 칼슘 양은 0.7g 이다.
        assertThat(criteriaForTestWoman.getProtein()).isEqualTo(55);
        assertThat(criteriaForTestWoman.getCalcium()).isEqualTo(0.7);
    }

    @Test
    @DisplayName("회원의 정보를 통해 BMR(기초대사량)을 조회할 수 있다.")
    void t002(){

        double BMRForTestMan = criteriaService.calcMemberKcal(testMan.getGender(), testMan.getAge(),
                testMan.getHeight(), testMan.getWeight());

        double BMRForTestWoman = criteriaService.calcMemberKcal(testWoman.getGender(), testWoman.getAge(),
                testWoman.getHeight(), testWoman.getWeight());

        //해리스-베네딕트 계산을 사용한 남성/여성의 기초대사량(BMR) 공식
        double testManBMR = 88.4 + (13.4 * 70.0) + (4.8 * 180.0) - (5.68 * 30);
        double testWomanBMR = 447.6 + (9.25 * 45.0) + (3.1 * 160.0) - (4.33 * 25);

        assertThat(BMRForTestMan).isEqualTo(testManBMR);
        assertThat(BMRForTestWoman).isEqualTo(testWomanBMR);
    }

    @Test
    @DisplayName("오늘 먹은 음식의 영양소를 계산할 수 있다.")
    void t003(){

        //testMan이 단백질 20, 지방 15의 음식을 2개 섭취
        dailyMenuService.create(testMan, foodInfoService.save(testFood1), 2L);

        //testWoman이 단백질 20, 지방 15의 음식을 2개 섭취하고, 칼슘 0.1, 나트륨 0.2의 음식을 3개 섭취함
        dailyMenuService.create(testWoman, foodInfoService.save(testFood1), 1L);
        dailyMenuService.create(testWoman, foodInfoService.save(testFood2), 3L);

        List<DailyMenu> testManMenuList = dailyMenuService.findMembersTodayMenuList(testMan);
        List<DailyMenu> testWomanMenuList = dailyMenuService.findMembersTodayMenuList(testWoman);

        Map<String, Double> testManCalcMap = criteriaService.calcTodayNutrition(testManMenuList).getData();
        Map<String, Double> testWomanCalcMap = criteriaService.calcTodayNutrition(testWomanMenuList).getData();

        assertThat(testManCalcMap.get("단백질")).isEqualTo(40);
        assertThat(testManCalcMap.get("지방")).isEqualTo(30);

        assertThat(testWomanCalcMap.get("단백질")).isEqualTo(20);
        assertThat(testWomanCalcMap.get("지방")).isEqualTo(15);
        assertThat(testWomanCalcMap.get("칼슘")).isEqualTo(0.3);
        assertThat(testWomanCalcMap.get("나트륨")).isEqualTo(0.6);

    }


}