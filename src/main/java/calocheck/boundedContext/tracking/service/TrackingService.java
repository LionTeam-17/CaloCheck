package calocheck.boundedContext.tracking.service;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrackingService {

    private final TrackingRepository trackingRepository;

    @Autowired
    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    public Tracking createTracking(Member member, LocalDate dateTime, Double weight, Double bodyFat, Double muscleMass) {
        Tracking tracking = Tracking.builder()
                .member(member)
                .dateTime(dateTime)
                .weight(weight)
                .bodyFat(bodyFat)
                .muscleMass(muscleMass)
                .build();

        return trackingRepository.save(tracking);
    }

    public List<Tracking> findTrackingsByMember(Member member) {
        return trackingRepository.findByMember(member);
    }

    public Tracking createTracking(Tracking tracking) {
        return trackingRepository.save(tracking);
    }
}
