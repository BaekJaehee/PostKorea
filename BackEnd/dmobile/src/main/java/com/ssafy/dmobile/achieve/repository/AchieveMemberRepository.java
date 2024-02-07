package com.ssafy.dmobile.achieve.repository;

import com.ssafy.dmobile.achieve.entity.AchieveMember;
import com.ssafy.dmobile.achieve.entity.AchieveMemberKey;
import com.ssafy.dmobile.visit.entity.MemberRelic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchieveMemberRepository extends JpaRepository<AchieveMember, AchieveMemberKey> {

    List<AchieveMember> findByMemberId(Long memberId);
}