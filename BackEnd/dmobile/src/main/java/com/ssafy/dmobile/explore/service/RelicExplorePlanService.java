package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.entity.RelicExplorePlan;
import com.ssafy.dmobile.explore.repository.RelicExplorePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelicExplorePlanService {

    private final RelicExplorePlanRepository repository;

    @Transactional
    public void addRelicToPlan(Long planId, Long relicId, Long memberId) {

        RelicExplorePlan newPlan = new RelicExplorePlan();
        newPlan.setPlanId(planId);
        newPlan.setRelicId(relicId);
        newPlan.setMemberId(memberId);
        repository.save(newPlan);
    }

    @Transactional
    public void updateRelicToPlan(Long planId, Long relicId, Long memberId, boolean visited) {

        Optional<RelicExplorePlan> optionalPlan = repository.findByPlanIdAndRelicIdAndMemberId(planId, relicId, memberId);
        if (optionalPlan.isEmpty()) {
            throw new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION);
        }

        RelicExplorePlan currentPlan = optionalPlan.get();

        currentPlan.setVisited(visited);
        repository.save(currentPlan);
    }

    @Transactional
    public void deleteRelicToPlan(Long planId, Long relicId, Long memberId) {

        Optional<RelicExplorePlan> optionalPlan = repository.findByPlanIdAndRelicIdAndMemberId(planId, relicId, memberId);
        if (optionalPlan.isEmpty()) {
            throw new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION);
        }

        RelicExplorePlan currentPlan = optionalPlan.get();
        repository.delete(currentPlan);
    }
}
