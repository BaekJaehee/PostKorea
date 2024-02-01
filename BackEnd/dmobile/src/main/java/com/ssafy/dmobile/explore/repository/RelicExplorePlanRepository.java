package com.ssafy.dmobile.explore.repository;

import com.ssafy.dmobile.explore.entity.RelicExplorePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelicExplorePlanRepository extends JpaRepository<RelicExplorePlan, Long> {
    Optional<RelicExplorePlan> findByPlanIdAndRelicIdAndUserId(Long planId, Long relicId, Long userId);
}
