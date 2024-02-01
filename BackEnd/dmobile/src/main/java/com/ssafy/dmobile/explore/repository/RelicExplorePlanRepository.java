package com.ssafy.dmobile.explore.repository;

import com.ssafy.dmobile.explore.entity.RelicExplorePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelicExplorePlanRepository extends JpaRepository<RelicExplorePlan, Long> {
    Optional<RelicExplorePlan> findByPlanIdAndRelicIdAndMemberId(Long planId, Long relicId, Long memberId);
}
