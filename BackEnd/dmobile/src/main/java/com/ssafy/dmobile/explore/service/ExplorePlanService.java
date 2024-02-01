package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.entity.ExplorePlan;
import com.ssafy.dmobile.explore.repository.ExplorePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ExplorePlanService {

    private final ExplorePlanRepository explorePlanRepository;

    @Transactional
    public ExplorePlan createPlan(Long userId, ExplorePlan explorePlan) {
        explorePlan.setUserId(userId);
        return explorePlanRepository.save(explorePlan);
    }

    @Transactional
    public ExplorePlan updatePlan(Long planId, Long userId, ExplorePlan explorePlanDetails) {

        ExplorePlan explorePlan = explorePlanRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        // 해당 계획을 수립한 유저가 아니면 삭제 불가능
        if (!Objects.equals(explorePlan.getUserId(), userId)) {
            throw new CustomException(ExceptionType.INVALID_USER_FOR_PLAN_EXCEPTION);
        };

        explorePlan.setPlanName(explorePlanDetails.getPlanName());
        explorePlan.setPlanStartDate(explorePlanDetails.getPlanStartDate());
        explorePlan.setPlanEndDate(explorePlanDetails.getPlanEndDate());
        explorePlan.setPlanCondition(explorePlanDetails.getPlanCondition());

        return explorePlanRepository.save(explorePlan);
    }

    @Transactional
    public void deletePlan(Long planId, Long userId) {

        ExplorePlan explorePlan = explorePlanRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        // 해당 계획에 권한이 있는 유저가 아니면 삭제 불가능
        if (!Objects.equals(explorePlan.getUserId(), userId)) {
            throw new CustomException(ExceptionType.INVALID_USER_FOR_PLAN_EXCEPTION);
        };

        explorePlanRepository.deleteById(planId);
    }

    public List<ExplorePlan> getOngoingPlans(Long id) {
        return explorePlanRepository.findOngoingPlans(id, new Date());
    }

    public List<ExplorePlan> getUpcomingPlans(Long id) {
        return explorePlanRepository.findUpcomingPlans(id, new Date());
    }

    public List<ExplorePlan> getCompletedPlans(Long id) {
        return explorePlanRepository.findCompletedPlans(id, new Date());
    }
}
