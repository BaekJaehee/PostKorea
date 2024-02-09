package com.ssafy.dmobile.achievements.repository;

import com.ssafy.dmobile.achievements.entity.achieve.Achieve;
import com.ssafy.dmobile.achievements.entity.achieve.AchieveMember;
import com.ssafy.dmobile.achievements.entity.achieve.AchieveMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AchieveMemberRepository extends JpaRepository<AchieveMember, AchieveMemberKey> {

    List<AchieveMember> findByMemberId(Long memberId);

    @Query("select am.achieve from AchieveMember am where am.member.id = :memberId")
    List<Achieve> findByAchievesOwnedByMember(@Param("memberId") Long memberId);

    @Query("select a from Achieve a where a.achieveId not in " +
            "(select am.achieve.achieveId from AchieveMember am where am.member.id = :memberId)")
    List<Achieve> findByAchievesNotOwnedByMember(@Param("memberId") Long memberId);
}