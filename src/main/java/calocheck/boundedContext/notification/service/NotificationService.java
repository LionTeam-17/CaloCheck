package calocheck.boundedContext.notification.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.notification.entity.Notification;
import calocheck.boundedContext.notification.repository.NotificationRepository;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.postLike.entity.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<Notification> findByPost_Member(Member member) {
        return notificationRepository.findByPost_Member(member);
    }

    public RsData<Notification> createPostLikeNotification(PostLike postLike, Post post, Member member, String type) {
        return make(postLike, null, post, member, "POSTLIKE");
    }

    public RsData<Notification> createCommentNotification(Comment comment, Post post, Member member, String type) {
        return make(null, comment, post, member, "COMMENT");
    }

    private RsData<Notification> make(PostLike postLike, Comment comment, Post post, Member member, String type) {
        Notification notification = Notification
                .builder()
                .postLike(postLike)
                .post(post)
                .comment(comment)
                .member(member)
                .noticeType(type)
                .build();

        notificationRepository.save(notification);

        return RsData.of("S-1", "알림 메세지가 생성되었습니다.", notification);
    }

    public RsData<Notification> updateReadDate(List<Notification> notifications) {
        notifications
                .stream()
                .filter(x -> x.getReadDate() == null)
                .forEach(x -> x.setReadDate(LocalDateTime.now()));

        return RsData.of("S-1", "읽음 처리 되었습니다.");
    }

    public RsData<Notification> deleteNotification(List<Notification> notifications) {
        List<Notification> readNotifications = notifications
                .stream()
                .filter(Notification::isRead)
                .toList();

        if (!readNotifications.isEmpty()) {
            notificationRepository.deleteAll(readNotifications);
        }

        return RsData.of("S-1", "알림 삭제");
    }
}
