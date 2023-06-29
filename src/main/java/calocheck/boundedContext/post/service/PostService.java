package calocheck.boundedContext.post.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public RsData<Post> savePost(String subject, String content, String photoUrl, final Member member) {
        Post post = Post.builder()
                .member(member)
                .subject(subject)
                .content(content)
                .photoUrl(photoUrl)
                .build();

        postRepository.save(post);

        return RsData.of("S-1", "게시물이 등록되었습니다.", post);
    }

    public RsData<Post> modifyPost(final Long id, String subject, String content, Member member) {
        Optional<Post> oPost = postRepository.findById(id);

        if (oPost.isEmpty()) {
            return RsData.of("F-1", "수정할 수 있는 글이 없습니다.");
        }

        if (!oPost.get().getMember().equals(member)) {
            return RsData.of("F-2", "수정 권한이 없습니다.");
        }

        Post post = oPost.get();

        Post modifyPost = post.toBuilder()
                .subject(subject)
                .content(content)
                .build();

        postRepository.save(modifyPost);

        return RsData.of("S-1", "게시글을 성공적으로 수정했습니다.", modifyPost);
    }

    public RsData<Post> deletePost(final Long id, final Long memberId) {
        Optional<Post> oPost = postRepository.findById(id);
        if (oPost.isEmpty())
            return RsData.of("F-1", "삭제할 수 있는 게시물이 없습니다.");

        if (!oPost.get().getMember().getId().equals(memberId))
            return RsData.of("F-2", "삭제 권한이 없습니다.");

        postRepository.delete(oPost.get());

        return RsData.of("S-1", "게시물이 삭제되었습니다.");
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    //-- paging --//
    @Transactional(readOnly = true)
    public Page<Post> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Post> findTop3ByOrderByPopularityDesc() {
        return postRepository.findTop3ByOrderByPopularityDesc();
    }

    @Transactional(readOnly = true)
    public List<Post> findBySubjectLike(String kw) {
        return postRepository.findBySubjectLike(kw);
    }

    @Transactional(readOnly = true)
    public Page<Post> findBySubjectLike(String kw, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findBySubjectLike(kw, pageable);
    }
}
