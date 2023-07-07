package calocheck.boundedContext.notification.eventListener;

import calocheck.base.event.EventAfterCreateComment;
import calocheck.base.event.EventAfterCreatePostLike;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.notification.service.NotificationService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.postLike.entity.PostLike;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationEventListener {
    private final NotificationService notificationService;

    @EventListener
    public void listen(EventAfterCreatePostLike event) {
        PostLike postLike = event.getPostLike();
        Post post = postLike.getPost();
        Member member = postLike.getMember();
        String type = "POSTLIKE";

        notificationService.createPostLikeNotification(postLike, post, member, type);
    }

    @EventListener
    public void listen(EventAfterCreateComment event) {
        Comment comment = event.getComment();
        Post post = comment.getPost();
        Member member = comment.getMember();
        String type = "COMMENT";

        notificationService.createCommentNotification(comment, post, member, type);
    }
}
