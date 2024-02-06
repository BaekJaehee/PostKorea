package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.entity.RelicExplorePlan;
import com.ssafy.dmobile.explore.entity.RelicExplorePlanKey;
import com.ssafy.dmobile.explore.repository.ExplorePlanRepository;
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
    private final ExplorePlanRepository explorePlanRepository;
    private final DetailDataRepository detailDataRepository;

    public List<DetailData> getRelicsInPlan(Long planId) {

        List<RelicExplorePlan> relicExplorePlans = relicExplorePlanRepository.findRelicExplorePlansByKeyPlanId(planId);

        List<DetailData> relicDetails = new ArrayList<>();
        for (RelicExplorePlan info : relicExplorePlans) {
            DetailData detailData = detailDataRepository.findByItemId(info.getKey().getRelicId());
            relicDetails.add(detailData);
        }

        return relicDetails;
    }

    @Transactional
    public void addRelicInPlan(Long planId, Long relicId) {

        RelicExplorePlanKey key = new RelicExplorePlanKey();
        key.setPlanId(planId);
        key.setRelicId(relicId);

        RelicExplorePlan newPlan = new RelicExplorePlan();
        newPlan.setKey(key);
        newPlan.setExplorePlan(explorePlanRepository.getReferenceById(planId));
        newPlan.setDetailData(detailDataRepository.getReferenceById(relicId));
        newPlan.setVisited(false);

        relicExplorePlanRepository.save(newPlan);
    }

    @Transactional
    public void addRelicListInPlan(Long planId, List<Long> relicIds) {

        for(Long relicId : relicIds) {
            addRelicInPlan(planId, relicId);
        }
    }

    @Transactional
    public void updateRelicInPlan(Long planId, Long relicId, boolean visited) {

        RelicExplorePlan currentPlan = relicExplorePlanRepository
                .findByKeyPlanIdAndKeyRelicId(planId, relicId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        currentPlan.setVisited(visited);
        relicExplorePlanRepository.save(currentPlan);
    }

    @Transactional
    public void updateRelicListInPlan(Long planId, List<Long> relicIds, boolean visited) {

        for (Long relicId : relicIds) {
            updateRelicInPlan(planId, relicId, visited);
        }
    }

    @Transactional
    public void deleteRelicInPlan(Long planId, Long relicId) {

        RelicExplorePlan currentPlan = relicExplorePlanRepository
                .findByKeyPlanIdAndKeyRelicId(planId, relicId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        relicExplorePlanRepository.delete(currentPlan);
    }

    @Transactional
    public void deleteRelicListInPlan(Long planId, List<Long> relicIds) {

        for (Long relicId : relicIds) {


            deleteRelicInPlan(planId, relicId);
        }
    }

    @Transactional
    public void deleteAllRelicsInPlan(Long planId) {

        relicExplorePlanRepository.deleteAllByKeyPlanId(planId);
    }
}
