package calocheck.boundedContext.tracking.service;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrackingService {

    private final TrackingRepository trackingRepository;

    @Autowired
    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    public Tracking createTracking(Member member, LocalDate dateTime, Integer age, Double height, Double weight, Double bodyFat, Double muscleMass, Double bmi, Double bodyFatPercentage) {
        Tracking tracking = Tracking.builder()
                .member(member)
                .dateTime(dateTime)
                .age(age)
                .height(height)
                .weight(weight)
                .bodyFat(bodyFat)
                .muscleMass(muscleMass)
                .bmi(bmi)
                .bodyFatPercentage(bodyFatPercentage)
                .build();

        calculateBMI(tracking);
        calculateBodyFatPercentage(tracking);
        calculateChanges(tracking);


        return trackingRepository.save(tracking);
    }

    public List<Tracking> findTrackingsByMember(Member member) {
        return trackingRepository.findByMember(member);
    }

    public Tracking createTracking(Tracking tracking) {
        calculateBMI(tracking);
        calculateBodyFatPercentage(tracking);
        calculateChanges(tracking);
        return trackingRepository.save(tracking);
    }

    public Tracking updateTracking(Tracking tracking) {
        calculateBMI(tracking);
        calculateBodyFatPercentage(tracking);
        calculateChanges(tracking);

        return trackingRepository.save(tracking);
    }

    public Optional<Tracking> findByMemberAndDate(Member member, LocalDate date) {
        return trackingRepository.findByMemberAndDateTime(member, date);
    }
    public void calculateBMI(Tracking tracking) {
        if (tracking.getHeight() != null && tracking.getWeight() != null && tracking.getHeight() != 0) {
            double heightInMeters = tracking.getHeight() / 100.0; // Convert height to meters
            double bmi = tracking.getWeight() / (heightInMeters * heightInMeters);
            tracking.setBmi(bmi);
        } else {
            tracking.setBmi(0.0);
        }
    }

    public void calculateBodyFatPercentage(Tracking tracking) {
        if (tracking.getWeight() != null && tracking.getBodyFat() != null) {
            double bodyFatPercentage = (tracking.getBodyFat() / tracking.getWeight()) * 100;
            tracking.setBodyFatPercentage(bodyFatPercentage);
        } else {
            tracking.setBodyFatPercentage(0.0);
        }
    }

    public void calculateChanges(Tracking current) {
        Tracking previous = trackingRepository.findTopByMemberAndDateTimeLessThanOrderByDateTimeDesc(current.getMember(), current.getDateTime());

        if (previous != null) {
            double weightChange = current.getWeight() != null && previous.getWeight() != null ? current.getWeight() - previous.getWeight() : 0.0;
            double bodyFatChange = current.getBodyFat() != null && previous.getBodyFat() != null ? current.getBodyFat() - previous.getBodyFat() : 0.0;
            double muscleMassChange = current.getMuscleMass() != null && previous.getMuscleMass() != null ? current.getMuscleMass() - previous.getMuscleMass() : 0.0;

            current.setWeightChange(weightChange);
            current.setBodyFatChange(bodyFatChange);
            current.setMuscleMassChange(muscleMassChange);
        } else {
            current.setWeightChange(0.0);
            current.setBodyFatChange(0.0);
            current.setMuscleMassChange(0.0);
        }
    }

    public Tracking findLatestTracking(Member member) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return trackingRepository.findTopByMemberAndDateTimeBeforeOrderByDateTimeDesc(member, tomorrow);
    }


}
