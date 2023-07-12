package calocheck.boundedContext.post.service;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
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
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("포스트 작성")
    void t001() {
        Member member = memberService.findById(1L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        assertThat(post.getSubject()).isEqualTo("테스트 게시물입니다.");
        assertThat(post.getContent()).isEqualTo("테스트 내용입니다.");
        assertThat(post.getPostType()).isEqualTo("A");
        assertThat(post.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("포스트 수정")
    void t002() {
        Member member = memberService.findById(2L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        postService.modifyPost(post.getId(), "게시물 수정 테스트입니다.", "게시물 수정 테스트 내용입니다.", "B", member);

        assertThat(post.getSubject()).isEqualTo("게시물 수정 테스트입니다.");
        assertThat(post.getContent()).isEqualTo("게시물 수정 테스트 내용입니다.");
        assertThat(post.getPostType()).isEqualTo("B");
        assertThat(post.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("포스트 삭제")
    void t003() {
        Member member = memberService.findById(3L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        postService.deletePost(post.getId(), member.getId());

        assertThat(postService.findById(post.getId()).isPresent()).isFalse();
    }
}
