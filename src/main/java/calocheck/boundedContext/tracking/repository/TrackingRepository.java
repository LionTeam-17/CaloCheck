package calocheck.boundedContext.tracking.repository;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.tracking.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByMember(Member member);
}