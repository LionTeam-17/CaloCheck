package calocheck.boundedContext.post.repository;

import calocheck.boundedContext.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
