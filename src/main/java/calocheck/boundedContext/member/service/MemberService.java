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
            String username, String password, String name, String email, String nickname, Integer age,
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

    public Member read(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
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
