package calocheck.boundedContext.member.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.service.TrackingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;
    private final PostService postService;
    private final TrackingService trackingService;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class JoinForm {
        @NotBlank
        @Size(min = 4, max = 20)
        private final String username;
        @NotBlank
        @Size(min = 4, max = 20)
        private final String password;
        @NotBlank
        private String gender;
        @NotBlank
        @Email(message = "유효하지 않은 이메일입니다.")
        private final String email;
        @NotBlank
        @Size(min = 2, max = 20)
        private final String nickname;
        private Integer age;
        private Double height;
        private Double weight;
        private Double muscleMass;
        private Double bodyFat;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "usr/member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {

        RsData<Member> checkRsData = memberService.checkDuplicateValue(joinForm.getUsername(), joinForm.getEmail(), joinForm.getNickname());

        if (checkRsData.isFail()) {
            return rq.historyBack(checkRsData.getMsg());
        }

        if (joinForm.getAge() == null) {
            joinForm.setAge(0);
        }

        if (joinForm.getHeight() == null) {
            joinForm.setHeight(0.0);
        }

        if (joinForm.getWeight() == null) {
            joinForm.setWeight(0.0);
        }

        if (joinForm.getMuscleMass() == null) {
            joinForm.setMuscleMass(0.0);
        }

        if (joinForm.getBodyFat() == null) {
            joinForm.setBodyFat(0.0);
        }

        RsData<Member> joinRs = memberService.join(
                joinForm.getUsername(), joinForm.getPassword(), joinForm.getGender(), joinForm.getEmail(), joinForm.getNickname(),
                joinForm.getAge(), joinForm.getHeight(), joinForm.getWeight(), joinForm.getMuscleMass(), joinForm.getBodyFat()
        );

        if (joinRs.isSuccess()) {
            Member member = joinRs.getData();
            Tracking tracking = new Tracking();
            tracking.setMember(member);
            tracking.setAge(member.getAge());
            tracking.setHeight(member.getHeight());
            tracking.setDateTime(LocalDate.now());
            tracking.setWeight(member.getWeight());
            tracking.setBodyFat(member.getBodyFat());
            tracking.setMuscleMass(member.getMuscleMass());
            trackingService.calculateBMI(tracking);
            trackingService.calculateBodyFatPercentage(tracking);
            trackingService.createTracking(tracking);
        }
        String msg = joinRs.getMsg() + "\n로그인 후 이용해주세요.";
        return rq.redirectWithMsg("/member/login", joinRs);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{memberId}")
    public String showModify(@PathVariable Long memberId) {
        return "usr/member/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{memberId}")
    public String modify(@PathVariable Long memberId, Integer age, Double height, Double weight, Double muscleMass, Double bodyFat) {

        RsData modifyRsData = memberService.modify(memberId, age, height, weight, muscleMass, bodyFat, rq.getMember());

        if (modifyRsData.isFail()) {
            return rq.historyBack(modifyRsData);
        }

        return rq.redirectWithMsg("/member/mypage/{memberId}", modifyRsData);
    }


    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {

        return "usr/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public String showMe() {

        return "usr/member/me";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/nickname/{id}")
    public ResponseEntity<String> updateNickname(@PathVariable Long id, @RequestParam("nickname") String nickname) {
        Member member = rq.getMember();

        RsData updateRsData = memberService.updateNickname(member, id, nickname);

        if (updateRsData.isFail()) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + updateRsData.getMsg() + "\"}");
        }

        return ResponseEntity.ok().body("{\"message\": \"닉네임이 %s(으)로 수정되었습니다.\"}".formatted(nickname));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/email/{id}")
    public ResponseEntity<String> updateEmail(@PathVariable Long id, @RequestParam("email") String email) {
        Member member = rq.getMember();

        RsData updateRsData = memberService.updateEmail(member, id, email);

        if (updateRsData.isFail()) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + updateRsData.getMsg() + "\"}");
        }

        return ResponseEntity.ok().body("{\"message\": \"이메일이 %s(으)로 수정되었습니다.\"}".formatted(email));
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/{id}")
    public String showMyPage(
            Model model,
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        // 회원 정보를 가져옵니다.
        Member member = memberService.findById(id).orElse(null);

        // 회원이 작성한 모든 글을 조회합니다.
        List<Post> allPosts = postService.findByMemberId(rq.getMember().getId());

        int totalPosts = allPosts.size();
        int totalPages = (int) Math.ceil((double) totalPosts / size);

        // 페이징 처리를 위한 글 목록을 구합니다.
        int startIdx = page * size;
        int endIdx = Math.min(startIdx + size, allPosts.size());
        List<Post> pagedPosts = allPosts.subList(startIdx, endIdx);

        // 조회된 글 목록을 모델에 추가합니다.
        model.addAttribute("postList", pagedPosts);

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);

        return "usr/member/mypage";
    }
}
