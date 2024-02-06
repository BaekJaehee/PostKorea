package com.ssafy.dmobile.explore.repository;

import com.ssafy.dmobile.explore.entity.MemberExplorePlan;
import com.ssafy.dmobile.explore.entity.MemberExplorePlanKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberExplorePlanRepository extends JpaRepository<MemberExplorePlan, MemberExplorePlanKey> {

    int countByKeyMemberIdAndKeyPlanId(Long memberId, Long planId);
}
