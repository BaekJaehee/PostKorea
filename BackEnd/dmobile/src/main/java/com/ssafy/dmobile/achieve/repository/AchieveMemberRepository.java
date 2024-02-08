package com.ssafy.dmobile.achieve.repository;

import com.ssafy.dmobile.achieve.entity.Achieve;
import com.ssafy.dmobile.achieve.entity.AchieveMember;
import com.ssafy.dmobile.achieve.entity.AchieveMemberKey;
import com.ssafy.dmobile.visit.entity.MemberRelic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface AchieveMemberRepository extends JpaRepository<AchieveMember, AchieveMemberKey> {

//    List<AchieveMember> findByMemberId(Long memberId);

    @Query("select am.achieve from AchieveMember am where am.member.id = :memberId")
    List<Achieve> findByAchievesOwnedByMember(@Param("memberId") Long memberId);

    @Query("select a from Achieve a where a.achieveId not in " +
            "(select am.achieve.achieveId from AchieveMember am where am.member.id = :memberId)")
    List<Achieve> findByAchievesNotOwnedByMember(@Param("memberId") Long memberId);
}