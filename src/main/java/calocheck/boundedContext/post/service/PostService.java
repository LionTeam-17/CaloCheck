package calocheck.boundedContext.post.service;

import calocheck.base.rsData.RsData;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
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
    private final MemberService memberService;

    public RsData<Post> savePost(String subject, String content, String postType, final Member member) {
        Post post = Post.builder()
                .member(member)
                .subject(subject)
                .content(content)
                .postType(postType)
                .build();

        postRepository.save(post);

        return RsData.of("S-1", "게시물이 등록되었습니다.", post);
    }

    public RsData<Post> modifyPost(final Long id, String subject, String content, String postType, Member member) {
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
                .postType(postType)
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
    public List<Post> findByMemberId(Long id) {
        return postRepository.findByMemberId(id);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    //-- paging --//
    @Transactional(readOnly = true)
    public Page<Post> findAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Post> findByPostType(String postType) {
        return postRepository.findByPostType(postType);
    }

    //-- paging --//
    @Transactional(readOnly = true)
    public Page<Post> findByPostType(String postType, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByPostType(postType, pageable);
    }

    @Transactional(readOnly = true)
    public List<Post> findByPostTypeOrderByPopularityDesc(String postType) {
        return postRepository.findByPostTypeOrderByPopularityDesc(postType);
    }

    @Transactional(readOnly = true)
    public Page<Post> findByPostTypeOrderByPopularityDesc(String postType, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByPostTypeOrderByPopularityDesc(postType, pageable);
    }

    @Transactional(readOnly = true)
    public List<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLike(String postType, String subjectKw, String nicknameKw) {
        return postRepository.findByPostTypeAndSubjectLikeOrMemberNicknameLike(postType, subjectKw, nicknameKw);
    }

    @Transactional(readOnly = true)
    public Page<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLike(String postType, String subjectKw, String nicknameKw, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByPostTypeAndSubjectLikeOrMemberNicknameLike(postType, subjectKw, nicknameKw, pageable);
    }

    @Transactional(readOnly = true)
    public List<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc(String postType, String subjectKw, String nicknameKw) {
        return postRepository.findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc(postType, subjectKw, nicknameKw);
    }

    @Transactional(readOnly = true)
    public Page<Post> findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc(String postType, String subjectKw, String nicknameKw, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByPostTypeAndSubjectLikeOrMemberNicknameLikeOrderByPopularityDesc(postType, subjectKw, nicknameKw, pageable);
    }

    public Page<Post> findByMemberId(Long id, Pageable pageable) {
        return postRepository.findByMemberId(id, pageable);
    }
}