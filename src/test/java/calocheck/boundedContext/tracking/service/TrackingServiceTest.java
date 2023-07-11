package calocheck.boundedContext.tracking.service;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.repository.TrackingRepository;
import calocheck.boundedContext.tracking.service.TrackingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TrackingServiceTest {

    @Mock
    private TrackingRepository trackingRepository;

    @InjectMocks
    private TrackingService trackingService;

    @Captor
    private ArgumentCaptor<Tracking> trackingCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("트래킹 생성 테스트")
    public void t001() {
        // Given
        Member member = new Member();
        LocalDate dateTime = LocalDate.now();
        Integer age = 25;
        Double height = 170.0;
        Double weight = 65.0;
        Double bodyFat = 20.0;
        Double muscleMass = 50.0;

        // Calculate BMI and Body Fat Percentage
        Double bmi = weight / ((height / 100.0) * (height / 100.0));
        Double bodyFatPercentage = (bodyFat / weight) * 100.0;

        Tracking savedTracking = new Tracking();
        when(trackingRepository.save(any(Tracking.class))).thenReturn(savedTracking);

        // When
        Tracking createdTracking = trackingService.createTracking(member, dateTime, age, height, weight, bodyFat, muscleMass, null, null);

        // Then
        verify(trackingRepository).save(trackingCaptor.capture());
        Tracking capturedTracking = trackingCaptor.getValue();
        Assertions.assertEquals(member, capturedTracking.getMember());
        Assertions.assertEquals(dateTime, capturedTracking.getDateTime());
        Assertions.assertEquals(age, capturedTracking.getAge());
        Assertions.assertEquals(height, capturedTracking.getHeight());
        Assertions.assertEquals(weight, capturedTracking.getWeight());
        Assertions.assertEquals(bodyFat, capturedTracking.getBodyFat());
        Assertions.assertEquals(muscleMass, capturedTracking.getMuscleMass());
        Assertions.assertEquals(Math.round(bmi * 100.0) / 100.0, Math.round(capturedTracking.getBmi() * 100.0) / 100.0);
        Assertions.assertEquals(Math.round(bodyFatPercentage * 100.0) / 100.0, Math.round(capturedTracking.getBodyFatPercentage() * 100.0) / 100.0);
        Assertions.assertEquals(savedTracking, createdTracking);
    }
    @Test
    @DisplayName("멤버별 트래킹 조회 테스트")
    public void t002() {
        // Given
        Member member = Member.builder().id(1L).build();

        Tracking tracking1 = Tracking.builder().id(1L).build();
        Tracking tracking2 = Tracking.builder().id(2L).build();
        List<Tracking> expectedTrackings = List.of(tracking1, tracking2);
        when(trackingRepository.findByMember(member)).thenReturn(expectedTrackings);

        // When
        List<Tracking> actualTrackings = trackingService.findTrackingsByMember(member);

        // Then
        Assertions.assertEquals(expectedTrackings, actualTrackings);
    }

    @Test
    @DisplayName("트래킹 업데이트 테스트")
    void t003() {
        // 테스트에 필요한 가짜 데이터 생성
        long memberId = 1;
        Member member = new Member();
        member.setAge(30);
        member.setHeight(170.0);
        member.setWeight(70.0);
        member.setBodyFat(15.0);
        member.setMuscleMass(60.0);

        LocalDate currentDate = LocalDate.now();

        Tracking existingTracking = new Tracking();
        existingTracking.setMember(member);
        existingTracking.setDateTime(currentDate);
        existingTracking.setWeight(70.0);
        existingTracking.setBodyFat(15.0);
        existingTracking.setMuscleMass(60.0);

        Tracking updatedTracking = new Tracking();
        updatedTracking.setMember(member);
        updatedTracking.setDateTime(currentDate);
        updatedTracking.setWeight(65.0);
        updatedTracking.setBodyFat(14.0);
        updatedTracking.setMuscleMass(61.0);

        // 가짜 데이터에 대한 동작 정의
        when(memberService.findById(memberId)).thenReturn(Optional.of(member));
        when(trackingRepository.findByMemberAndDate(member, currentDate)).thenReturn(Optional.of(existingTracking));
        when(trackingRepository.updateTracking(existingTracking)).thenReturn(updatedTracking);

        // 업데이트 수행
        Tracking result = trackingService.updateTracking(updatedTracking);

        // 예상 결과와 실제 결과 비교
        Assertions.assertEquals(updatedTracking.getWeight(), result.getWeight());
        Assertions.assertEquals(updatedTracking.getBodyFat(), result.getBodyFat());
        Assertions.assertEquals(updatedTracking.getMuscleMass(), result.getMuscleMass());
    }
}




    @Test
    @DisplayName("멤버와 날짜로 트래킹 조회 테스트")
    public void t004() {
        // Given
        Member member = new Member();
        LocalDate date = LocalDate.now();

        Tracking tracking = new Tracking();
        when(trackingRepository.findByMemberAndDateTime(member, date)).thenReturn(Optional.of(tracking));

        // When
        Optional<Tracking> foundTracking = trackingService.findByMemberAndDate(member, date);

        // Then
        Assertions.assertTrue(foundTracking.isPresent());
        Assertions.assertEquals(tracking, foundTracking.get());
    }

    @Test
    @DisplayName("BMI 계산 테스트")
    public void t005() {
        // Given
        Tracking tracking = new Tracking();
        tracking.setHeight(170.0);
        tracking.setWeight(65.0);

        // When
        trackingService.calculateBMI(tracking);

        // Then
        Assertions.assertEquals(22.491, tracking.getBmi(), 0.001);
    }

    @Test
    @DisplayName("체지방률 계산 테스트")
    public void t006() {
        // Given
        Tracking tracking = new Tracking();
        tracking.setWeight(65.0);
        tracking.setBodyFat(20.0);

        // When
        trackingService.calculateBodyFatPercentage(tracking);

        // Then
        Assertions.assertEquals(30.769, tracking.getBodyFatPercentage(), 0.001);
    }

    @Test
    @DisplayName("변화 계산 테스트 - 이전 트래킹 없음")
    public void t007() {
        // Given
        Tracking currentTracking = new Tracking();
        currentTracking.setWeight(70.0);
        currentTracking.setBodyFat(22.0);
        currentTracking.setMuscleMass(52.0);

        when(trackingRepository.findTopByMemberAndDateTimeLessThanOrderByDateTimeDesc(any(Member.class), any(LocalDate.class)))
                .thenReturn(null);

        // When
        trackingService.calculateChanges(currentTracking);

        // Then
        Assertions.assertEquals(0.0, currentTracking.getWeightChange());
        Assertions.assertEquals(0.0, currentTracking.getBodyFatChange());
        Assertions.assertEquals(0.0, currentTracking.getMuscleMassChange());
    }

    @Test
    @DisplayName("최신 트래킹 조회 테스트")
    public void t008() {
        // Given
        Member member = new Member();

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        Tracking latestTracking = new Tracking();
        when(trackingRepository.findTopByMemberAndDateTimeBeforeOrderByDateTimeDesc(member, tomorrow))
                .thenReturn(latestTracking);

        // When
        Tracking foundTracking = trackingService.findLatestTracking(member);

        // Then
        Assertions.assertEquals(latestTracking, foundTracking);
    }
}
