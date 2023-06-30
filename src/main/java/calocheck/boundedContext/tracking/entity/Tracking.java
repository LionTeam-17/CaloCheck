package calocheck.boundedContext.tracking.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Tracking extends BaseEntity {
    @ManyToOne
    private Member member;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTime;
    private Integer age;
    private Double height;
    private Double weight;
    private Double bodyFat;
    private Double muscleMass;

}
