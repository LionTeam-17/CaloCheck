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
        calculateChanges(tracking, trackingRepository.findTopByMemberOrderByDateTimeDesc(member));


        return trackingRepository.save(tracking);
    }

    public List<Tracking> findTrackingsByMember(Member member) {
        return trackingRepository.findByMember(member);
    }

    public Tracking createTracking(Tracking tracking) {
        calculateChanges(tracking, trackingRepository.findTopByMemberOrderByDateTimeDesc(tracking.getMember()));
        return trackingRepository.save(tracking);
    }

    public Tracking updateTracking(Tracking tracking) {
        calculateBMI(tracking);
        calculateBodyFatPercentage(tracking);
        calculateChanges(tracking, trackingRepository.findTopByMemberOrderByDateTimeDesc(tracking.getMember()));

        return trackingRepository.save(tracking);
    }

    public Optional<Tracking> findByMemberAndDate(Member member, LocalDate date) {
        return trackingRepository.findByMemberAndDateTime(member, date);
    }
    public void calculateBMI(Tracking tracking) {
        if (tracking.getHeight() != null && tracking.getWeight() != null) {
            double heightInMeters = tracking.getHeight() / 100; // Convert height to meters
            double bmi = tracking.getWeight() / (heightInMeters * heightInMeters);
            tracking.setBmi(bmi);
        }
    }

    public void calculateBodyFatPercentage(Tracking tracking) {
        if (tracking.getWeight() != null && tracking.getBodyFat() != null) {
            double bodyFatPercentage = (tracking.getBodyFat() / tracking.getWeight()) * 100;
            tracking.setBodyFatPercentage(bodyFatPercentage);
        }
    }

    public void calculateChanges(Tracking current, Tracking previous) {
        if (previous != null) {
            current.setWeightChange(current.getWeight() - previous.getWeight());
            current.setBodyFatChange(current.getBodyFat() - previous.getBodyFat());
            current.setMuscleMassChange(current.getMuscleMass() - previous.getMuscleMass());
        }
    }

}
