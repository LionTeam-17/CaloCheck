package calocheck.boundedContext.criteria.service;

import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@Transactional
class CriteriaServiceTest {

    @Autowired
    private CriteriaService criteriaService;
    @Autowired
    private MemberService memberService;

    private Member testMan;
    private Member testWoman;

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
    }

    @Test
    @DisplayName("실행시 권장량이 엑셀 파일을 통해 자동으로 생성되며, 성별과 나이를 통해 자신의 권장량을 확인할 수 있다.")
    void t001(){

        Criteria criteriaForTestMan = criteriaService.findByGenderAndAge(testMan);
        Criteria criteriaForTestWoman = criteriaService.findByGenderAndAge(testWoman);

        //30세 남성의 단백질 권장량은 65g 이고, 권장 칼슘 양은 0.8g 이다.
        assertThat(criteriaForTestMan.getProtein()).isEqualTo(65);
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

}