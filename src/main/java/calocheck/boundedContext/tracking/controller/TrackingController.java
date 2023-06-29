package calocheck.boundedContext.tracking.controller;

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

    @Autowired
    public TrackingController(TrackingService trackingService, MemberService memberService) { // Inject MemberService
        this.trackingService = trackingService;
        this.memberService = memberService;
    }

    @GetMapping("/tracking/{memberId}")
    public String showTracking(@PathVariable("memberId") Long memberId, Model model) {
        Optional<Member> memberOptional = memberService.findById(memberId);

        if (!memberOptional.isPresent()) {
            // Handle error. For example, you can return a error message or error page.
            return "error/memberNotFound";
        }

        Member member = memberOptional.get();
        List<Tracking> trackingData = trackingService.findTrackingsByMember(member);
        model.addAttribute("trackingData", trackingData); // Add trackingData to the model
        model.addAttribute("tracking", new Tracking()); // Add an empty Tracking object to the model
        return "usr/tracking/bodyTracking";
    }

    @PostMapping("/tracking")
    public String createTracking(@ModelAttribute("tracking")
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Tracking tracking,
                                 Principal principal) {
        // Retrieve the logged in member
        String currentPrincipalName = principal.getName();
        Member member = memberService.findByUsername(currentPrincipalName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + currentPrincipalName));

        // Set member in tracking data
        tracking.setMember(member);

        // Check if tracking data for the given date already exists
        Optional<Tracking> existingTracking = trackingService.findByMemberAndDate(member, tracking.getDateTime());

        Tracking savedTracking;
        if (existingTracking.isPresent()) {
            // Update existing tracking data
            Tracking trackingToUpdate = existingTracking.get();
            trackingToUpdate.setWeight(tracking.getWeight());
            trackingToUpdate.setBodyFat(tracking.getBodyFat());
            trackingToUpdate.setMuscleMass(tracking.getMuscleMass());

            savedTracking = trackingService.updateTracking(trackingToUpdate);
        } else {
            // Save new tracking data
            savedTracking = trackingService.createTracking(tracking);
        }

        // Redirect to tracking page
        return "redirect:/tracking/" + savedTracking.getMember().getId();
    }

}
