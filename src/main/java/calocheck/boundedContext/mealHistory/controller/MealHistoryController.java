package calocheck.boundedContext.mealHistory.controller;

import calocheck.boundedContext.mealHistory.dto.MealHistoryDto;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mealHistory")
public class MealHistoryController {

    private final MealHistoryService mealHistoryService;
    private final MemberService memberService;

    public MealHistoryController(MealHistoryService mealHistoryService, MemberService memberService) {
        this.mealHistoryService = mealHistoryService;
        this.memberService = memberService;
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MealHistoryDto> showMealHistory(@PathVariable Long memberId) {
        Optional<Member> memberOpt = memberService.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }

        Member member = memberOpt.get();
        MealHistory mealHistory = mealHistoryService.getByMember(member);

        MealHistoryDto mealHistoryDto = MealHistoryDto.fromEntity(mealHistory);

        // Modify the mealHistoryDto here to include only the necessary fields

        return ResponseEntity.ok(mealHistoryDto);
    }

    @GetMapping("/api/{memberId}")
    public ResponseEntity<List<MealHistoryDto>> findMealHistoryByDate(@PathVariable Long memberId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Optional<Member> memberOpt = memberService.findById(memberId);
        if (!memberOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }

        Member member = memberOpt.get();
        List<MealHistory> mealHistoryByDate = mealHistoryService.findByMemberAndDate(member, date);

        List<MealHistoryDto> mealHistoryDtos = mealHistoryByDate.stream()
                .map(MealHistoryDto::fromEntity)
                .collect(Collectors.toList());

        // Modify the mealHistoryDtos here to include only the necessary fields

        return ResponseEntity.ok(mealHistoryDtos);
    }
}
