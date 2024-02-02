package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.entity.ExplorePlan;
import com.ssafy.dmobile.explore.entity.RelicExplorePlan;
import com.ssafy.dmobile.explore.entity.RelicExplorePlanKey;
import com.ssafy.dmobile.explore.repository.ExplorePlanRepository;
import com.ssafy.dmobile.explore.repository.RelicExplorePlanRepository;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
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

        RelicExplorePlanKey key = new RelicExplorePlanKey();
        key.setPlanId(planId);
        key.setRelicId(relicId);
        key.setMemberId(memberId);

        RelicExplorePlan newPlan = new RelicExplorePlan();
        newPlan.setKey(key);

        repository.save(newPlan);
    }

    @Transactional
    public void updateRelicToPlan(Long planId, Long relicId, Long memberId, boolean visited) {

        RelicExplorePlan currentPlan = repository
                .findByKeyPlanIdAndKeyRelicIdAndKeyMemberId(planId, relicId, memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        currentPlan.setVisited(visited);
        repository.save(currentPlan);
    }

    @Transactional
    public void deleteRelicToPlan(Long planId, Long relicId, Long memberId) {

        RelicExplorePlan currentPlan = repository
                .findByKeyPlanIdAndKeyRelicIdAndKeyMemberId(planId, relicId, memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        repository.delete(currentPlan);
    }
}
