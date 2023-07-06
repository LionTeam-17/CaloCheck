package calocheck.boundedContext.notification.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.base.util.Ut;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.postLike.entity.PostLike;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Notification extends BaseEntity {
    @Setter
    private LocalDateTime readDate;
    @ManyToOne
    @ToString.Exclude
    private Member member;
    @ManyToOne
    @ToString.Exclude
    private Post post;
    @ManyToOne
    @ToString.Exclude
    private Comment comment;
    @ManyToOne
    @ToString.Exclude
    private PostLike postLike;
    private String noticeType;

    public boolean isRead() {
        if (getReadDate() != null) return Ut.time.isRead(LocalDateTime.now(), getReadDate());
        return false;
    }
}
