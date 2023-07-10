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
    private Double bmi;
    private Double bodyFatPercentage;

    private Double weightChange;
    private Double bodyFatChange;
    private Double muscleMassChange;

    public void setWeight(Double weight) {
        this.weight = Math.round(weight * 100) / 100.0; // Round to 2 decimal places
    }

    public void setBodyFat(Double bodyFat) {
        this.bodyFat = Math.round(bodyFat * 100) / 100.0; // Round to 2 decimal places
    }

    public void setMuscleMass(Double muscleMass) {
        this.muscleMass = Math.round(muscleMass * 100) / 100.0; // Round to 2 decimal places
    }

    public void setBmi(Double bmi) {
        this.bmi = Math.round(bmi * 100) / 100.0; // Round to 2 decimal places
    }

    public void setBodyFatPercentage(Double bodyFatPercentage) {
        this.bodyFatPercentage = Math.round(bodyFatPercentage * 100) / 100.0; // Round to 2 decimal places
    }

    public void setWeightChange(Double weightChange) {
        this.weightChange = Math.round(weightChange * 100) / 100.0; // Round to 2 decimal places
    }

    public void setBodyFatChange(Double bodyFatChange) {
        this.bodyFatChange = Math.round(bodyFatChange * 100) / 100.0; // Round to 2 decimal places
    }

    public void setMuscleMassChange(Double muscleMassChange) {
        this.muscleMassChange = Math.round(muscleMassChange * 100) / 100.0; // Round to 2 decimal places
    }

}
