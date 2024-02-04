package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.entity.ExplorePlan;
import com.ssafy.dmobile.explore.entity.dto.ExplorePlanDto;
import com.ssafy.dmobile.explore.repository.ExplorePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExplorePlanService {

    private final ExplorePlanRepository explorePlanRepository;

    @Transactional
    public ExplorePlan createPlan(Long memberId, ExplorePlanDto explorePlanDto) {

        ExplorePlan explorePlan = new ExplorePlan();

        explorePlan.setMemberId(memberId);
        explorePlan.setPlanName(explorePlanDto.getPlanName());
        explorePlan.setPlanStartDate(explorePlanDto.getPlanStartDate());
        explorePlan.setPlanEndDate(explorePlanDto.getPlanEndDate());
        explorePlan.setPlanCondition(explorePlanDto.getPlanCondition());

        return explorePlanRepository.save(explorePlan);
    }

    @Transactional
    public ExplorePlan updatePlan(Long planId, Long memberId, ExplorePlanDto modifiedExplorePlan) {

        ExplorePlan explorePlan = explorePlanRepository
                .findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        // 해당 계획을 수립한 유저가 아니면 수정 불가능
        if (!explorePlan.getMemberId().equals(memberId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        explorePlan.setPlanName(modifiedExplorePlan.getPlanName());
        explorePlan.setPlanStartDate(modifiedExplorePlan.getPlanStartDate());
        explorePlan.setPlanEndDate(modifiedExplorePlan.getPlanEndDate());
        explorePlan.setPlanCondition(modifiedExplorePlan.getPlanCondition());

        return explorePlanRepository.save(explorePlan);
    }

    @Transactional
    public void deletePlan(Long planId, Long memberId) {

        ExplorePlan explorePlan = explorePlanRepository
                .findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        // 해당 계획에 권한이 있는 유저가 아니면 삭제 불가능
        if (!explorePlan.getMemberId().equals(memberId)) {
            throw new CustomException(ExceptionType.INVALID_MEMBER_FOR_PLAN_EXCEPTION);
        }

        explorePlanRepository.deleteById(planId);
    }

    public List<ExplorePlan> getOngoingPlans(Long memberId) {
        return explorePlanRepository.findOngoingPlans(memberId, new Date().getTime());
    }

    public List<ExplorePlan> getUpcomingPlans(Long memberId) {
        return explorePlanRepository.findUpcomingPlans(memberId, new Date().getTime());
    }

    public List<ExplorePlan> getCompletedPlans(Long memberId) {
        return explorePlanRepository.findCompletedPlans(memberId, new Date().getTime());
    }
}
