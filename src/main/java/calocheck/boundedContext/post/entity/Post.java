package calocheck.boundedContext.post.entity;

import calocheck.base.entity.BaseEntity;
import calocheck.base.util.Ut;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Post extends BaseEntity {
    @Column(length = 30)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;
    @Builder.Default
    private Long popularity = 0L;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();
    private String photoUrl;

    public Boolean isToday() {
        return Ut.time.isToday(LocalDateTime.now(), getCreateDate());
    }
}