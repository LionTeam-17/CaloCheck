package calocheck.boundedContext.notification.repository;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByPost_Member(Member member);

    int countByPost_MemberAndReadDateIsNull(Member member);
}
