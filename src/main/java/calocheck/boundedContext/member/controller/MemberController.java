package calocheck.boundedContext.member.controller;

import calocheck.base.rq.Rq;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.service.CriteriaService;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.service.DailyMenuService;
import calocheck.boundedContext.friend.entity.Friend;
import calocheck.boundedContext.friend.service.FriendService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.service.TrackingService;
import calocheck.boundedContext.tracking.repository.TrackingRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;
    private final PostService postService;
    private final TrackingService trackingService;
    private final TrackingRepository trackingRepository;
    private final FriendService friendService;
    private final CriteriaService criteriaService;
    private final DailyMenuService dailyMenuService;

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

    // 회원가입 페이지로 이동
    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "usr/member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {

        // 회원가입 시 이메일, 아이디, 비밀번호, 닉네임 유효성 체크를 합니다.
        RsData<Member> checkRsData = memberService.checkDuplicateValue(joinForm.getUsername(), joinForm.getEmail(), joinForm.getNickname());

        // 유효하지 못한 회원가입 시 오류메세지와 함께 히스토리백.
        if (checkRsData.isFail()) {
            return rq.historyBack(checkRsData.getMsg());
        }

        // 선택사항 미입력시 기본 defalut값 => 0
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

        // 회원가입 폼에서 얻은 정보로 회원가입
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

    // 회원의 인적사항 수정폼 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{memberId}")
    public String showModify(@PathVariable Long memberId, Model model) {
        // 이전에 입력된 회원 정보 가져오기
        Member member = memberService.findById(memberId).orElse(null);

        // 회원 정보를 모델에 설정하여 폼 페이지로 전달
        model.addAttribute("member", member);

        return "usr/member/modify";
    }

    // 회원 수정 폼 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{memberId}")
    public String modify(@PathVariable Long memberId, String gender, Integer age, Double height, Double weight, Double muscleMass, Double bodyFat) {

        // 수정 정보 가져와서 회원정보에 저장
        RsData modifyRsData = memberService.modify(memberId, gender, age, height, weight, muscleMass, bodyFat, rq.getMember());

        if (modifyRsData.isFail()) {
            return rq.historyBack(modifyRsData);
        }

        return rq.redirectWithMsg("/member/mypage/{memberId}", modifyRsData);
    }

    // 로그인 페이지로 이동.
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {

        return "usr/member/login";
    }

    // 내 정보 페이지로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public String showMe() {

        return "usr/member/me";
    }

    // 닉네임 변경 처리
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

    // 이메일 변경 처리
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

    // 마이페이지로 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/{id}")
    public String showMyPage(
            Model model,
            @PathVariable Long id,
            @PageableDefault(sort = "createDate", direction = DESC, size = 5) Pageable pageable
    ) {
        // 회원 정보를 가져옵니다.
        Member member = memberService.findById(id).orElse(null);

        // 회원이 작성한 모든 글을 조회합니다.
        Page<Post> allPosts = postService.findByMemberId(id, pageable);

        // 조회된 글 목록을 모델에 추가합니다.
        model.addAttribute("postList", allPosts);
        model.addAttribute("page", allPosts.getNumber());
        model.addAttribute("totalPages", allPosts.getTotalPages());

        model.addAttribute("member", member);
        model.addAttribute("id", id);

        // 트래킹에서 최신 정보 가져오기
        Tracking latestTracking =  trackingService.findLatestTracking(member);

        // 회원 정보에 최신 트래킹 정보 반영
        member.setWeight(latestTracking.getWeight());
        member.setBodyFat(latestTracking.getBodyFat());
        member.setMuscleMass(latestTracking.getMuscleMass());

        // 친구
        List<Member> followingList = friendService.findFollowing(id);
        List<Member> followerList = friendService.findFollower(id);
        Friend follow = friendService.findByFollowerIdAndFollowingId(rq.getMember().getId(), id).orElse(null);

        // 조회한 친구 목록을 모델에 추가합니다.
        model.addAttribute("member", member);
        model.addAttribute("follow", follow);

        model.addAttribute("followingList", followingList);
        model.addAttribute("followerList", followerList);

        Criteria myCriteria = criteriaService.findByGenderAndAge(member);

        List<DailyMenu> todayMenuList = dailyMenuService.findByMembersTodayMenuList(member);
        RsData<Map<String, Double>> todayCalcRsData = criteriaService.calcTodayNutrition(todayMenuList);

        if(todayCalcRsData.isSuccess()){
            model.addAttribute("calcMap",todayCalcRsData.getData());
        }

        model.addAttribute("myBMR", member.getBmr());

        model.addAttribute("myCriteria", myCriteria);

        return "usr/member/mypage";
    }
}
