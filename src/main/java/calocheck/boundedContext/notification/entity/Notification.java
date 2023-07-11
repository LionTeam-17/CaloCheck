package calocheck.boundedContext.notification.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.base.util.Ut;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.postLike.entity.PostLike;
import jakarta.persistence.*;
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
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Post post;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Comment comment;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PostLike postLike;
    private String noticeType;

    public boolean isRead() {
        if (getReadDate() != null) return Ut.time.isRead(LocalDateTime.now(), getReadDate());
        return false;
    }
}
