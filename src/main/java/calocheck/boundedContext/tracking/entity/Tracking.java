package calocheck.boundedContext.tracking.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.boundedContext.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Tracking extends BaseEntity {
    @ManyToOne
    private Member member;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTime;
    private Double weight;
    private Double bodyFat;
    private Double muscleMass;

    public LocalDate getDateTime() {
        return this.dateTime;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Double getBodyFat() {
        return this.bodyFat;
    }

    public Double getMuscleMass() {
        return this.muscleMass;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setBodyFat(Double bodyFat) {
        this.bodyFat = bodyFat;
    }

    public void setMuscleMass(Double muscleMass) {
        this.muscleMass = muscleMass;
    }

}
