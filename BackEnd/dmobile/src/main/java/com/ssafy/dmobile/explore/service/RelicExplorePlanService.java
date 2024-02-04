package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.entity.RelicExplorePlan;
import com.ssafy.dmobile.explore.entity.RelicExplorePlanKey;
import com.ssafy.dmobile.explore.repository.RelicExplorePlanRepository;
import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.repository.DetailDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelicExplorePlanService {

    private final RelicExplorePlanRepository relicExplorePlanRepository;
    private final DetailDataRepository detailDataRepository;

    @Transactional
    public void addRelicInPlan(Long planId, Long relicId, Long memberId) {

        RelicExplorePlanKey key = new RelicExplorePlanKey();
        key.setPlanId(planId);
        key.setRelicId(relicId);
        key.setMemberId(memberId);

        RelicExplorePlan newPlan = new RelicExplorePlan();
        newPlan.setKey(key);

        relicExplorePlanRepository.save(newPlan);
    }

    @Transactional
    public void updateRelicInPlan(Long planId, Long relicId, Long memberId, boolean visited) {

        RelicExplorePlan currentPlan = relicExplorePlanRepository
                .findByKeyPlanIdAndKeyRelicIdAndKeyMemberId(planId, relicId, memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        currentPlan.setVisited(visited);
        relicExplorePlanRepository.save(currentPlan);
    }

    @Transactional
    public void deleteRelicInPlan(Long planId, Long relicId, Long memberId) {

        RelicExplorePlan currentPlan = relicExplorePlanRepository
                .findByKeyPlanIdAndKeyRelicIdAndKeyMemberId(planId, relicId, memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        relicExplorePlanRepository.delete(currentPlan);
    }

    public List<DetailData> getRelicsInPlan(Long planId) {

        List<RelicExplorePlan> relicExplorePlans = relicExplorePlanRepository.findRelicExplorePlansByKeyPlanId(planId);

        List<DetailData> relicDetails = new ArrayList<>();
        for (RelicExplorePlan info : relicExplorePlans) {
            DetailData detailData = detailDataRepository.findByItemId(info.getKey().getRelicId());
            relicDetails.add(detailData);
        }

        return relicDetails;
    }

    public boolean canAccessPlan(Long memberId, Long planId) {
        // planId와 memberId로 조회하여 접근 가능 여부 확인
        int count = relicExplorePlanRepository.countByKeyPlanIdAndKeyMemberId(planId, memberId);
        return count > 0;
    }
}
