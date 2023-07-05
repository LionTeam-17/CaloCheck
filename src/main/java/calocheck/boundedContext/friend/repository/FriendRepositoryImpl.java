package calocheck.boundedContext.friend.repository;

import calocheck.boundedContext.friend.entity.QFriend;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QFriend friend = QFriend.friend;
    private final QMember member = QMember.member;

    @Override
    public List<Member> findFollowingByFollowerId(Long followerId) {
        return jpaQueryFactory
                .select(member)
                .from(friend, member)
                .where(friend.followingId.eq(member.id)
                        .and(friend.followerId.eq(followerId)))
                .fetch();
    }

    @Override
    public List<Member> findFollowerByFollowingId(Long followingId) {
        return jpaQueryFactory
                .select(member)
                .from(friend, member)
                .where(friend.followerId.eq(member.id)
                        .and(friend.followingId.eq(followingId)))
                .fetch();
    }
}
