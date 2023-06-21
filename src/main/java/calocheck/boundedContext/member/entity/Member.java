package calocheck.boundedContext.member.entity;

import calocheck.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String providerTypeCode; // 일반/소셜 중 무엇으로 가입했는지 확인용
    @Column(unique = true)
    private String username;
    private String password;
    private String name;

    private String email;

    @Column(unique = true)
    private String nickname;
    private int age;
    private double height;
    private double weight;
    private double muscleMass;
    private double bodyFat;
}
