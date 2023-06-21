package calocheck.boundedContext.post.entity;

import calocheck.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

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
}
