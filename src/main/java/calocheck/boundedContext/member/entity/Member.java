package calocheck.boundedContext.member.entity;

import calocheck.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String providerTypeCode; // 일반/소셜 중 무엇으로 가입했는지 확인용
    @Column(unique = true)
    private String username;
    private String password;
    private String gender;
    private String email;
    @Column(unique = true)
    private String nickname;
    private Integer age;
    private Double height;
    private Double weight;
    private Double muscleMass;
    private Double bodyFat;
    private Double bmr;


    // Member에 권한 부여 로직
    public List<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // 모든 회원에게 member 권한 부여
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));

        // 관리자에게는 admin 권한 부여
        if (isAdmin()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }

        return grantedAuthorities;
    }

    public boolean isAdmin() {
        return "admin".equals(username);
    }

}
