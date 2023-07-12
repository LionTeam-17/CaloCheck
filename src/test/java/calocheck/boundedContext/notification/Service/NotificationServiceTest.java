package calocheck.boundedContext.notification.Service;

import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.notification.service.NotificationService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.postLike.service.PostLikeService;
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
public class NotificationServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostLikeService postLikeService;
    @Autowired
    private NotificationService notificationService;

    @Test
    @DisplayName("댓글 작성 시에 알림")
    void t001() {
        Member member = memberService.findById(1L).get();
        Member member2 = memberService.findById(2L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        commentService.saveComment("테스트 댓글입니다.", post, member2).getData();

        assertThat(notificationService.findByPost_Member(member).get(0).getNoticeType()).isEqualTo("COMMENT");
    }

    @Test
    @DisplayName("포스트 좋아요 시 알림")
    void t002() {
        Member member = memberService.findById(1L).get();
        Member member2 = memberService.findById(2L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        postLikeService.savePostLike(post.getId(), member2).getData();

        assertThat(notificationService.findByPost_Member(member).get(0).getNoticeType()).isEqualTo("POSTLIKE");
    }

    @Test
    @DisplayName("본인 포스트에 본인이 댓글 작성 시 알림 안옴")
    void t003() {
        Member member = memberService.findById(1L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        commentService.saveComment("테스트 댓글입니다.", post, member).getData();

        assertThat(notificationService.findByPost_Member(member).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("본인 포스트에 본인이 좋아요 시 알림 안옴")
    void t004() {
        Member member = memberService.findById(1L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        postLikeService.savePostLike(post.getId(), member).getData();

        assertThat(notificationService.findByPost_Member(member).isEmpty()).isTrue();
    }
}
