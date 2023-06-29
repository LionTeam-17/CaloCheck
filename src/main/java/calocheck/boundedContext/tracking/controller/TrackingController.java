package calocheck.boundedContext.tracking.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
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

    @GetMapping("/tracking")
    public String showTracking(Model model) {
        Optional<Member> memberOptional = memberService.findById(rq.getMember().getId());

        if (!memberOptional.isPresent()) {
            return "error/memberNotFound";
        }

        Member member = memberOptional.get();
        List<Tracking> trackingData = trackingService.findTrackingsByMember(member);
        model.addAttribute("trackingData", trackingData);
        model.addAttribute("tracking", new Tracking());
        return "usr/tracking/bodyTracking";
    }

    @PostMapping("/tracking")
    public String createTracking(@ModelAttribute("tracking")
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Tracking tracking,
                                 Principal principal) {

        String currentPrincipalName = principal.getName();
        Member member = memberService.findByUsername(currentPrincipalName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + currentPrincipalName));

        tracking.setMember(member);

        Optional<Tracking> existingTracking = trackingService.findByMemberAndDate(member, tracking.getDateTime());

        Tracking savedTracking;
        if (existingTracking.isPresent()) {
            Tracking trackingToUpdate = existingTracking.get();
            trackingToUpdate.setWeight(tracking.getWeight());
            trackingToUpdate.setBodyFat(tracking.getBodyFat());
            trackingToUpdate.setMuscleMass(tracking.getMuscleMass());

            savedTracking = trackingService.updateTracking(trackingToUpdate);
        } else {
            savedTracking = trackingService.createTracking(tracking);
        }

        return "redirect:/tracking";
    }

}
