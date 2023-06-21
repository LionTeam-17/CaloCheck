package calocheck.boundedContext.post.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public RsData<Post> savePost(String subject,String content , final Member member){
        Post post = Post.builder()
                .member(member)
                .subject(subject)
                .content(content)
                .build();

        postRepository.save(post);

        return RsData.of("S-1", "댓글이 등록되었습니다.", post);
    }

    public RsData<Post> modifyPost(final Long id, String content, Member member) {
        Optional<Post> oPost = postRepository.findById(id);

        if (oPost.isEmpty()) {
            return RsData.of("F-1", "수정할 수 있는 글이 없습니다.");
        }

        if (!oPost.get().getMember().equals(member)) {
            return RsData.of("F-2", "수정 권한이 없습니다.");
        }

        Post post = oPost.get();

        Post modifyPost = post.toBuilder()
                .content(content)
                .build();

        postRepository.save(modifyPost);

        return RsData.of("S-1", "게시글을 성공적으로 수정했습니다.", modifyPost);
    }

    public RsData<Post> deletePost(final Long id, final Long memberId) {
        Optional<Post> oPost = postRepository.findById(id);
        if (oPost.isEmpty())
            return RsData.of("F-1", "삭제할 수 있는 댓글이 없습니다.");

        if (!oPost.get().getMember().getId().equals(memberId))
            return RsData.of("F-2", "삭제 권한이 없습니다.");

        postRepository.delete(oPost.get());

        return RsData.of("S-1", "댓글이 삭제되었습니다.");
    }
}
