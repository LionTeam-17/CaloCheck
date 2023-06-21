package calocheck.boundedContext.comment.repository;

import calocheck.boundedContext.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
