package calocheck.boundedContext.comment.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.repository.CommentRepository;
import calocheck.boundedContext.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public RsData<Comment> saveComment(String content, Post post, final Member member){
        Comment comment = Comment.builder()
                .member(member)
                .content(content)
                .post(post)
                .build();

        commentRepository.save(comment);

        return RsData.of("S-1", "댓글이 등록되었습니다.", comment);
    }

    public RsData<Comment> modifyComment(final Long id, String content, Member member) {
        Optional<Comment> oComment = commentRepository.findById(id);

        if (oComment.isEmpty()) {
            return RsData.of("F-1", "수정할 수 있는 댓글이 없습니다.");
        }

        if (!oComment.get().getMember().equals(member)) {
            return RsData.of("F-2", "수정 권한이 없습니다.");
        }

        Comment comment = oComment.get();

        Comment modifyComment = comment.toBuilder()
                .content(content)
                .build();

        commentRepository.save(modifyComment);

        return RsData.of("S-1", "댓글을 성공적으로 수정했습니다.",modifyComment);
    }

    public RsData<Comment> deleteComment(final Long id, final Long memberId) {
        Optional<Comment> oComment = commentRepository.findById(id);
        if (oComment.isEmpty())
            return RsData.of("F-1", "삭제할 수 있는 댓글이 없습니다.");

        if (!oComment.get().getMember().getId().equals(memberId))
            return RsData.of("F-2", "삭제 권한이 없습니다.");

        commentRepository.delete(oComment.get());

        return RsData.of("S-1", "댓글이 삭제되었습니다.");
    }
}
