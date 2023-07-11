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

        //testWoman은 25세 여성으로, 160의 키와 45의 몸무게를 가진다고 가정
        testWoman = Member.builder()
                .age(25)
                .gender("W")
                .height(160.0)
                .weight(45.0)
                .build();

    }

    /*
    1. 프로그램이 시작되었을 때, CriteriaConfig를 통해 내용이 자동 생성 되는지 확인
    2. 멤버를 통해 자신의 권장량 리스트를 가져올 수 있다.
    3. 멤버의 정보를 통해 기초대사량을 가져올 수 있다.
    4. 오늘 먹은 영양소 계산 가능
    5. (나의 권장량 - 오늘 먹은 영양소 - 지금 카트에 담긴 음식) 계산이 가능.
     */

    @Test
    @DisplayName("실행시 권장량이 엑셀 파일을 통해 자동으로 생성된다")
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



}