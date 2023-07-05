package calocheck.boundedContext.friend.repository;

import calocheck.boundedContext.member.entity.Member;

import java.util.List;

public interface FriendRepositoryCustom {

    List<Member> findFollowingByFollowerId(Long followerId);

    List<Member> findFollowerByFollowingId(Long followingId);
}
