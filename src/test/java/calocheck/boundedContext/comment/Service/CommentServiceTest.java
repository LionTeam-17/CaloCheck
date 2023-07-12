package calocheck.boundedContext.comment.Service;

import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
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
class CommentServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("게시물 댓글 생성")
    void t001() {
        Member member = memberService.findById(1L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        Comment comment = commentService.saveComment("테스트 댓글입니다.", post, member).getData();

        assertThat(comment.getPost()).isEqualTo(post);
        assertThat(comment.getMember()).isEqualTo(member);
        assertThat(comment.getContent()).isEqualTo("테스트 댓글입니다.");
    }

    @Test
    @DisplayName("게시물 댓글 삭제")
    void t002() {
        Member member = memberService.findById(1L).get();

        Post post = postService.savePost("테스트 게시물입니다.", "테스트 내용입니다.", "A", member).getData();

        Comment comment = commentService.saveComment("테스트 댓글입니다.", post, member).getData();

        commentService.deleteComment(comment.getId(), member.getId());

        assertThat(commentService.findById(comment.getId()).isPresent()).isFalse();
    }
}
