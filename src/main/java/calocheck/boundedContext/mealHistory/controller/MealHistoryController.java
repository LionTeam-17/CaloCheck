package calocheck.boundedContext.mealHistory.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.service.MealHistoryService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mealHistory")
public class MealHistoryController {

    private final MealHistoryService mealHistoryService;
    private final MemberService memberService;
    private Rq rq;

    public MealHistoryController(MealHistoryService mealHistoryService, MemberService memberService) {
        this.mealHistoryService = mealHistoryService;
        this.memberService = memberService;
    }

    @GetMapping("/{memberId}")
    public String showMealHistory(@PathVariable Long memberId, Model model) {
        Optional<Member> memberOpt = memberService.findById(memberId);
        if (!memberOpt.isPresent()) {
            return "redirect:/";
        }

        Member member = memberOpt.get();
        MealHistory mealHistory = mealHistoryService.getByMember(member);

        model.addAttribute("mealHistory", mealHistory);
        model.addAttribute("member", member);
        return "usr/mealHistory/mealHistory";
    }

//    @GetMapping("/mealHistory/{date}")
//    @PreAuthorize("isAuthenticated()")
//    public String showMealHistoryByDate(@PathVariable("date") String dateStr, Model model) {
//
//        LocalDate date = LocalDate.parse(dateStr); // 날짜 문자열을 LocalDate로 변환
//
//        Member member = rq.getMember();
//
//        // 선택한 날짜의 식사 이력을 가져옵니다.
//        List<MealHistory> mealHistoryByDate = mealHistoryService.findByMemberAndDate(member, date);
//
//        model.addAttribute("mealHistoryByDate", mealHistoryByDate);
//
//        return "usr/mealHistory/showByDate"; // 해당 날짜의 식사 이력을 보여주는 뷰로 이동
//    }

    @GetMapping("/api/{memberId}")
    public ResponseEntity<List<MealHistory>> getMealHistoryByDate(@PathVariable Long memberId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<Member> memberOpt = memberService.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }

        Member member = memberOpt.get();
        List<MealHistory> mealHistoryByDate = mealHistoryService.findByMemberAndDate(member, date);

        return ResponseEntity.ok(mealHistoryByDate);
    }
}