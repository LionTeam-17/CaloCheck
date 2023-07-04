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
import java.util.List;
import java.util.Optional;

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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bodyTracking")
    public String showTracking(Model model) {
        Optional<Member> memberOptional = memberService.findById(rq.getMember().getId());

        if (!memberOptional.isPresent()) {
            return "error/memberNotFound";
        }

        Member member = memberOptional.get();
        List<Tracking> trackingData = trackingService.findTrackingsByMember(member);
        model.addAttribute("member", member);
        model.addAttribute("trackingData", trackingData);
        model.addAttribute("tracking", new Tracking());
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

            savedTracking = trackingService.updateTracking(trackingToUpdate);
        } else {
            tracking.setWeight(member.getWeight());
            tracking.setBodyFat(member.getBodyFat());
            tracking.setMuscleMass(member.getMuscleMass());

            savedTracking = trackingService.createTracking(tracking);
        }

        return "redirect:/tracking/bodyTracking";
    }


}