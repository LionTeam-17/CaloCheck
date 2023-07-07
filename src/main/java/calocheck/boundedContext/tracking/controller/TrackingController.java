package calocheck.boundedContext.tracking.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.service.TrackingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/tracking")
@RequiredArgsConstructor
public class TrackingController {
    private final TrackingService trackingService;
    private final MemberService memberService;
    private final Rq rq;

    @Autowired
    public TrackingController(Rq rq, TrackingService trackingService, MemberService memberService) {
            this.rq =rq;
            this.trackingService = trackingService;
            this.memberService = memberService;
    }
    @GetMapping("/bodyTracking")
    public String showTracking(Model model) {
        Optional<Member> memberOptional = memberService.findById(rq.getMember().getId());

        if (!memberOptional.isPresent()) {
            return "error/memberNotFound";
        }

        Member member = memberOptional.get();
        List<Tracking> trackingData = trackingService.findTrackingsByMember(member);
        List<Tracking> trackingDataReverse = new ArrayList<>(trackingData);
        Collections.reverse(trackingDataReverse);

        model.addAttribute("member", member);
        model.addAttribute("trackingData", trackingData);
        model.addAttribute("trackingDataReverse", trackingDataReverse);
        model.addAttribute("tracking", new Tracking());

        List<String> dates = trackingDataReverse.stream()
                .map(tracking -> tracking.getDateTime().toString())
                .collect(Collectors.toList());
        List<Double> weights = trackingDataReverse.stream()
                .map(Tracking::getWeight)
                .collect(Collectors.toList());
        List<Double> bodyFats = trackingDataReverse.stream()
                .map(Tracking::getBodyFat)
                .collect(Collectors.toList());
        List<Double> muscleMasses = trackingDataReverse.stream()
                .map(Tracking::getMuscleMass)
                .collect(Collectors.toList());

        model.addAttribute("dates", dates);
        model.addAttribute("weights", weights);
        model.addAttribute("bodyFats", bodyFats);
        model.addAttribute("muscleMasses", muscleMasses);

        return "usr/tracking/bodyTracking";
    }
    @Transactional
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/bodyTracking")
    public String createTracking(@ModelAttribute("tracking")
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Tracking tracking,
                                 Principal principal) {

        String currentPrincipalName = principal.getName();
        Member member = memberService.findByUsername(currentPrincipalName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + currentPrincipalName));

        tracking.setMember(member);
        tracking.setDateTime(LocalDate.now());


        if (tracking.getAge() == null) {
            tracking.setAge(member.getAge());
        }
        if (tracking.getHeight() == null) {
            tracking.setHeight(member.getHeight());
        }
        if (tracking.getWeight() == null) {
            tracking.setWeight(member.getWeight());
        }
        if (tracking.getBodyFat() == null) {
            tracking.setBodyFat(member.getBodyFat());
        }
        if (tracking.getMuscleMass() == null) {
            tracking.setMuscleMass(member.getMuscleMass());
        }
        if (tracking.getBmi() == null) {
            trackingService.calculateBMI(tracking);
        }
        if (tracking.getBodyFatPercentage() == null) {
            trackingService.calculateBodyFatPercentage(tracking);
        }

        Optional<Tracking> existingTracking = trackingService.findByMemberAndDate(member, tracking.getDateTime());

        Tracking savedTracking;
        if (existingTracking.isPresent()) {
            Tracking trackingToUpdate = existingTracking.get();
            trackingToUpdate.setWeight(tracking.getWeight());
            trackingToUpdate.setBodyFat(tracking.getBodyFat());
            trackingToUpdate.setMuscleMass(tracking.getMuscleMass());

            trackingService.calculateBMI(trackingToUpdate);
            trackingService.calculateBodyFatPercentage(trackingToUpdate);
            trackingService.calculateChanges(trackingToUpdate, existingTracking.get());

            savedTracking = trackingService.updateTracking(trackingToUpdate);
        } else {
            tracking.setWeight(member.getWeight());
            tracking.setBodyFat(member.getBodyFat());
            tracking.setMuscleMass(member.getMuscleMass());

            trackingService.calculateBMI(tracking);
            trackingService.calculateBodyFatPercentage(tracking);
            trackingService.calculateChanges(tracking, null);

            savedTracking = trackingService.createTracking(tracking);
        }

        return "redirect:/tracking/bodyTracking";
    }


}
