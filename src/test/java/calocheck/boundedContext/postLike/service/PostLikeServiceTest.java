package calocheck.boundedContext.postLike.service;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.postLike.entity.PostLike;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.MethodName.class)
@Transactional
class PostLikeServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PostLikeService postLikeService;

    @Test
    @DisplayName("게시물 추천 생성")
    void t001() {
        Member member = memberService.findById(1L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        PostLike postLike = postLikeService.savePostLike(post.getId(), member).getData();

        assertThat(postLike.getPost()).isEqualTo(post);
        assertThat(postLike.getMember()).isEqualTo(member);
        assertThat(postLike.getPositive()).isEqualTo(true);
    }

    @Test
    @DisplayName("게시물 추천 삭제")
    void t002() {
        Member member = memberService.findById(2L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        PostLike postLike = postLikeService.savePostLike(post.getId(), member).getData();

        postLikeService.deletePostLike(post.getId(), member);

        assertThat(postLikeService.findById(postLike.getId()).isPresent()).isFalse();
    }
}
