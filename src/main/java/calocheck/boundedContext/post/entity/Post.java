package calocheck.boundedContext.post.entity;

import calocheck.base.entity.BaseEntity;
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

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Post extends BaseEntity {
    private String subject;
    private String content;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;
    @Builder.Default
    private Long popularity = 0L;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @Builder.Default
    private List<Comment> answerList = new ArrayList<>();
}
