package calocheck.boundedContext.criteria.controller;


import calocheck.base.rq.Rq;
import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.service.CriteriaService;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.service.MealHistoryService;
import calocheck.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/criteria")
@RequiredArgsConstructor
public class CriteriaController {

    private final Rq rq;
    private final CriteriaService criteriaService;
    private final MealHistoryService mealHistoryService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public String showCriteriaInfo(Model model){

        Member member = rq.getMember();

        Criteria byGenderAndAge = criteriaService.findByGenderAndAge(member);

        /*
        오늘 먹은 양 계산하는 시나리오

        mealHistory에서 createDate를 통해 오늘 먹은 내용들을 조회한다
         */

        List<MealHistory> byMemberAndCreateDate = mealHistoryService.findByMemberAndCreateDate(member);
        Map<String, Integer> calcTodayNutritionMap = mealHistoryService.calcTodayNutrition(byMemberAndCreateDate);

        model.addAttribute("calcMap",calcTodayNutritionMap);
        model.addAttribute("criteria", byGenderAndAge);

        return "/usr/criteria/criteria";
    }


}
