package calocheck.boundedContext.mealHistory.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.mealHistory.dto.MealHistoryDto;
import calocheck.boundedContext.mealHistory.entity.MealHistory;
import calocheck.boundedContext.mealHistory.service.MealHistoryService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/mealHistory")
public class MealHistoryController {

    private final MealHistoryService mealHistoryService;
    private final MemberService memberService;
    private final Rq rq;

    public MealHistoryController(MealHistoryService mealHistoryService, MemberService memberService, Rq rq) {
        this.mealHistoryService = mealHistoryService;
        this.memberService = memberService;
        this.rq = rq;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{memberId}")
    public String showMealHistoryToday(@PathVariable Long memberId, Model model) {
        String loggedInMemberId = rq.getMember().getId().toString();

        if (!loggedInMemberId.equals(String.valueOf(memberId))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Optional<Member> memberOpt = memberService.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }

        Member member = memberOpt.get();
        List<MealHistory> mealHistoriesToday = mealHistoryService.findMembersTodayHistory(member);

        List<MealHistoryDto> mealHistoryDtos = mealHistoriesToday.stream()
                .map(MealHistoryDto::fromEntity)
                .collect(Collectors.toList());

        model.addAttribute("member", member);
        model.addAttribute("mealHistories", mealHistoryDtos);

        return "usr/mealHistory/mealHistory";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/{memberId}")
    public ResponseEntity<List<MealHistoryDto>> findMealHistoryByDate(@PathVariable Long memberId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        String loggedInMemberId = rq.getMember().getId().toString();

        if (!loggedInMemberId.equals(String.valueOf(memberId))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        Optional<Member> memberOpt = memberService.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }

        Member member = memberOpt.get();
        List<MealHistory> mealHistoryByDate = mealHistoryService.findByMemberAndDate(member, date);

        List<MealHistoryDto> mealHistoryDtos = mealHistoryByDate.stream()
                .map(MealHistoryDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mealHistoryDtos);
    }
}