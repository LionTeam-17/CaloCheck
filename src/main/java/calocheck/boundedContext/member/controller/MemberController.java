package calocheck.boundedContext.member.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;
    @AllArgsConstructor
    @Getter
    public static class JoinForm {
        @NotBlank
        @Size(min = 4, max = 20)
        private final String username;
        @NotBlank
        @Size(min = 4, max = 20)
        private final String password;
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

        RsData<Member> joinRs = memberService.join(
                joinForm.getUsername(), joinForm.getPassword(), joinForm.getEmail(), joinForm.getNickname(),
                joinForm.getAge(), joinForm.getHeight(), joinForm.getWeight(), joinForm.getMuscleMass(), joinForm.getBodyFat()
        );

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

        return rq.redirectWithMsg("/member/mypage", modifyRsData);
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
    @GetMapping("/mypage")
    public String showMyPage() {

        return "usr/member/mypage";
    }
}
