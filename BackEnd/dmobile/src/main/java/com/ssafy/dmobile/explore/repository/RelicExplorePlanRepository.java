package com.ssafy.dmobile.explore.repository;

import com.ssafy.dmobile.explore.entity.RelicExplorePlan;
import com.ssafy.dmobile.explore.entity.RelicExplorePlanKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelicExplorePlanRepository extends JpaRepository<RelicExplorePlan, RelicExplorePlanKey> {
    Optional<RelicExplorePlan> findByKeyPlanIdAndKeyRelicIdAndKeyMemberId(Long planId, Long relicId, Long memberId);

    int countByKeyPlanIdAndKeyMemberId(Long planId, Long memberId);

    List<RelicExplorePlan> findRelicExplorePlansByKeyPlanId(Long planId);
}
