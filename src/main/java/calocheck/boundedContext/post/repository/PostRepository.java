package calocheck.boundedContext.post.repository;

import calocheck.boundedContext.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    List<Post> findByMemberId(Long id);

    List<Post> findAll();

    Page<Post> findAll(Pageable pageable);

    List<Post> findByPostType(String postType);

    Page<Post> findByPostType(String postType, Pageable pageable);

    Page<Post> findByMemberId(Long id, Pageable pageable);

    List<Post> findByPostTypeOrderByPopularityDesc(String postType);

    Page<Post> findByPostTypeOrderByPopularityDesc(String postType, Pageable pageable);

    List<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLike(String postType, String subjectKw, String nicknameKw);

    Page<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLike(String postType, String subjectKw, String nicknameKw, Pageable pageable);

    List<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc(String postType, String subjectKw, String nicknameKw);

    Page<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc(String postType, String subjectKw, String nicknameKw, Pageable pageable);
}
