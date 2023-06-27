package calocheck.boundedContext.post.repository;

import calocheck.boundedContext.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    List<Post> findAll();

    Page<Post> findAll(Pageable pageable);

    List<Post> findTop3ByOrderByPopularityDesc();

    List<Post> findBySubjectLike(String kw);

    Page<Post> findBySubjectLike(String kw, Pageable pageable);
}
