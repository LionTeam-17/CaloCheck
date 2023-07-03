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

        List<MealHistory> byMemberAndCreateDate = mealHistoryService.findByMemberAndCreateDate(member);
        Map<String, Integer> calcTodayNutritionMap = mealHistoryService.calcTodayNutrition(byMemberAndCreateDate);

        model.addAttribute("BMR", member.getBmr());
        model.addAttribute("calcMap",calcTodayNutritionMap);
        model.addAttribute("criteria", byGenderAndAge);

        return "/usr/criteria/criteria";
    }


}
