package calocheck.boundedContext.postLike.service;

import calocheck.base.event.EventAfterCreatePostLike;
import calocheck.base.rsData.RsData;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.repository.PostRepository;
import calocheck.boundedContext.postLike.entity.PostLike;
import calocheck.boundedContext.postLike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public RsData<PostLike> savePostLike(Long postId, Member member) {
        Optional<Post> oPost = postRepository.findById(postId);

        if (oPost.isEmpty()) {
            return RsData.of("F-1", "게시물이 없습니다.");
        }

        Post post = oPost.get();

        Optional<PostLike> oPostLike = postLikeRepository.findByPostAndMember(post, member);
        if (oPostLike.isPresent()) {
            return RsData.of("F-2", "이미 추천했습니다.");
        }

        Post updatePostLike = post.toBuilder()
                .popularity(post.getPopularity() + 1)
                .build();

        postRepository.save(updatePostLike);

        PostLike postLike = PostLike.builder()
                .positive(true)
                .member(member)
                .post(post)
                .build();

        postLikeRepository.save(postLike);

        eventPublisher.publishEvent(new EventAfterCreatePostLike(postLike));

        return RsData.of("S-1", "추천했습니다.", postLike);
    }

    public RsData<PostLike> deletePostLike(Long postId, Member member) {
        Optional<Post> oPost = postRepository.findById(postId);

        if (oPost.isEmpty()) {
            return RsData.of("F-1", "게시물이 없습니다.");
        }

        Post post = oPost.get();

        Optional<PostLike> oPostLike = postLikeRepository.findByPostAndMember(post, member);
        if (oPostLike.isEmpty()) {
            return RsData.of("F-2", "이미 취소했습니다.");
        }

        Post updatePostLike = post.toBuilder()
                .popularity(post.getPopularity() - 1)
                .build();

        postRepository.save(updatePostLike);

        postLikeRepository.delete(oPostLike.get());

        return RsData.of("S-1", "추천을 취소했습니다.", oPostLike.get());
    }
}
