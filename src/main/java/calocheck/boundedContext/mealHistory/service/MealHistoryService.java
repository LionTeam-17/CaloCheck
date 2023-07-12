package calocheck.boundedContext.mealHistory.service;

import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.service.CriteriaService;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.repository.MealHistoryRepository;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.nutrient.dto.NutrientDTO;
import calocheck.boundedContext.tag.config.TagConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealHistoryService {

    private final MealHistoryRepository mealHistoryRepository;
    private final CriteriaService criteriaService;
    private final TagConfigProperties tagConfigProp;

    public MealHistory create(Member member, List<DailyMenu> dailyMenuList, String mealType, String mealMemo, int mealScore) {
        MealHistory mealHistory = MealHistory.builder()
                .member(member)
                .dailyMenuList(dailyMenuList)
                .mealType(mealType)
                .mealMemo(mealMemo)
                .mealScore(mealScore)
                .build();

        return mealHistoryRepository.save(mealHistory);
    }

    public MealHistory update(MealHistory mealHistory, Member member, List<DailyMenu> dailyMenuList, String mealType, String mealMemo, int mealScore) {
        MealHistory updated = mealHistory.toBuilder()
                .member(member)
                .dailyMenuList(dailyMenuList)
                .mealType(mealType)
                .mealMemo(mealMemo)
                .mealScore(mealScore)
                .build();

        return mealHistoryRepository.save(updated);
    }

    public void delete(MealHistory mealHistory) {
        mealHistoryRepository.delete(mealHistory);
    }

    public MealHistory findById(Long id) {
        Optional<MealHistory> mealHistory = mealHistoryRepository.findById(id);

        return mealHistory.orElse(null);
    }

    public MealHistory getByMember(Member member) {
        return mealHistoryRepository.findByMember(member).orElse(null);
    }

    public List<MealHistory> findMembersTodayHistory(Member member) {
        LocalDateTime startDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return mealHistoryRepository.findByMemberAndCreateDateBetween(member, startDateTime, endDateTime);
    }

    public List<MealHistory> findByMemberAndDate(Member member, LocalDate date) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(23, 59, 59);
        return mealHistoryRepository.findByMemberAndCreateDateBetween(member, startDateTime, endDateTime);
    }

    public MealHistory findByMemberAndMealTypeAndCreateDateBetween(Member member, String mealType) {
        LocalDateTime startDateTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDateTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return mealHistoryRepository.findByMemberAndMealTypeAndCreateDateBetween(member, mealType, startDateTime, endDateTime);
    }

    public List<MealHistory> findByMemberAndCurrentMonth(Member member) {
        LocalDateTime startDateTime = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime endDateTime = YearMonth.now().atEndOfMonth().atTime(23,59,59);
        return mealHistoryRepository.findByMemberAndCreateDateBetween(member, startDateTime, endDateTime);
    }
}