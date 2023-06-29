package calocheck.boundedContext.tracking.repository;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.tracking.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByMember(Member member);

    Optional<Tracking> findByMemberAndDateTime(Member member, LocalDate date);
}