package calocheck.boundedContext.member.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    public Member create(
            String username, String password, String email, String nickname, Integer age,
            Double height, Double weight, Double muscleMass,  Double bodyFat
                         ) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .age(age)
                .height(height)
                .weight(weight)
                .muscleMass(muscleMass)
                .bodyFat(bodyFat)
                .build();
        return memberRepository.save(member);
    }
    
    public void delete(Member member) {
        memberRepository.delete(member);
    }
    @Transactional
    public RsData<Member> join(
            String username, String password, String email, String nickname,
            Integer age, Double height, Double weight, Double muscleMass, Double bodyFat
    ) {

        return join("Calocheck", username, password, email, nickname, age, height,weight, muscleMass, bodyFat);
    }

    @Transactional
    public RsData<Member> join(
            String providerTypeCode, String username, String password, String email, String nickname,
            Integer age, Double height, Double weight, Double muscleMass, Double bodyFat
    ) {
        Member member = Member
                .builder()
                .providerTypeCode(providerTypeCode)
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .age(age)
                .height(height)
                .weight(weight)
                .muscleMass(muscleMass)
                .bodyFat(bodyFat)
                .build();

        memberRepository.save(member);
        return RsData.of("S-1", "회원가입이 완료되었습니다.", member);
    }

    public RsData<Member> checkDuplicateValue(String username, String email, String nickname) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("F-1", "해당 아이디(%s)는 이미 사용중입니다.".formatted(username));
        }

        if (findByEmail(email).isPresent()) {
            return RsData.of("F-2", "해당 이메일(%s)은 이미 사용중입니다.".formatted(email));
        }

        if (findByNickname(nickname).isPresent()) {
            return RsData.of("F-3", "해당 닉네임(%s)은 이미 사용중입니다.".formatted(nickname));
        }

        return RsData.of("S-1", "가입 가능합니다.");
    }

    // 소셜 로그인
    @Transactional
    public RsData<Member> whenSocialLogin(String providerTypeCode, String username, String email, String nickname, Integer age, Double height, Double weight, Double muscleMass, Double bodyFat) {
        Optional<Member> opMember = findByUsername(username);

        if (opMember.isPresent()) return RsData.of("S-1", "로그인 되었습니다.", opMember.get());

        return join(providerTypeCode, username, "", email,  nickname,
                0, 0.0, 0.0,0.0, 0.0); // 최초 로그인시 실행
        //TODO : 닉네임 설정

    }

    @Transactional
    public RsData<Member> updateNickname(Member actor, Long memberId, String nickname) {

        if (actor.getId() != memberId) {
            return RsData.of("F-1", "사용자 정보가 일치하지 않습니다.");
        }

        Member checkMember = findByNickname(nickname).orElse(null);

        if (checkMember != null) {
            return RsData.of("F-S", "이미 사용 중인 닉네임입니다.");
        }

        actor = actor.toBuilder().nickname(nickname).build();
        memberRepository.save(actor);

        return RsData.of("S-1", "닉네임이 수정되었습니다.");
    }

    @Transactional
    public RsData<Member> updateInfo(Member actor, Long memberId2, Integer age, Double height, Double weight, Double muscleMass, Double bodyFat) {

        if (actor.getId() != memberId2) {
            return RsData.of("F-1", "사용자 정보가 일치하지 않습니다.");
        }


        actor = actor.toBuilder()
                .age(age)
                .height(height)
                .weight(weight)
                .muscleMass(muscleMass)
                .bodyFat(bodyFat)
                .build();
        memberRepository.save(actor);

        return RsData.of("S-1", "정보가 수정되었습니다.");
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }
}
