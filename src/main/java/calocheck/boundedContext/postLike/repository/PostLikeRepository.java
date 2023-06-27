package calocheck.boundedContext.postLike.repository;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndMember(Post post, Member member);
}
