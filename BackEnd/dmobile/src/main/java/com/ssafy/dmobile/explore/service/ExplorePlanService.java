package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.explore.entity.ExplorePlan;
import com.ssafy.dmobile.explore.entity.MemberExplorePlan;
import com.ssafy.dmobile.explore.entity.MemberExplorePlanKey;
import com.ssafy.dmobile.explore.entity.dto.ExplorePlanDto;
import com.ssafy.dmobile.explore.repository.ExplorePlanRepository;
import com.ssafy.dmobile.explore.repository.MemberExplorePlanRepository;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
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
    private final MemberExplorePlanRepository memberExplorePlanRepository;
    private final MemberRepository memberRepository;

    // 계획 세우기
    @Transactional
    public ExplorePlan createPlan(ExplorePlanDto explorePlanDto) {

        ExplorePlan explorePlan = new ExplorePlan();

        explorePlan.setPlanName(explorePlanDto.getPlanName());
        explorePlan.setPlanStartDate(explorePlanDto.getPlanStartDate());
        explorePlan.setPlanEndDate(explorePlanDto.getPlanEndDate());
        explorePlan.setPlanCondition(explorePlanDto.getPlanCondition());

        return explorePlanRepository.save(explorePlan);
    }

    // 계획에 맴버 매핑
    @Transactional
    public MemberExplorePlan connectMemberToExplorePlan(Long memberId, Long planId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));

        ExplorePlan plan = explorePlanRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        // 복합 키 인스턴스 생성
        MemberExplorePlanKey key = new MemberExplorePlanKey();
        key.setMemberId(member.getId());
        key.setPlanId(plan.getPlanId());

        MemberExplorePlan memberExplorePlan = new MemberExplorePlan();
        memberExplorePlan.setKey(key);
        memberExplorePlan.setMember(member);
        memberExplorePlan.setExplorePlan(plan);
        memberExplorePlanRepository.save(memberExplorePlan);

        return memberExplorePlan;
    }

    // 계획 정보 업데이트
    @Transactional
    public ExplorePlan updatePlan(Long planId, ExplorePlanDto explorePlanDto) {

        ExplorePlan explorePlan = explorePlanRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ExceptionType.PLAN_NOT_FOUND_EXCEPTION));

        explorePlan.setPlanName(explorePlanDto.getPlanName());
        explorePlan.setPlanStartDate(explorePlanDto.getPlanStartDate());
        explorePlan.setPlanEndDate(explorePlanDto.getPlanEndDate());
        explorePlan.setPlanCondition(explorePlanDto.getPlanCondition());

        return explorePlanRepository.save(explorePlan);
    }

    @Transactional
    public void deletePlan(Long planId) {
        explorePlanRepository.deleteById(planId);
    }

    public boolean canAccessPlan(Long memberId, Long planId) {
        // planId와 memberId로 조회하여 접근 가능 여부 확인
        int count = memberExplorePlanRepository.countByKeyMemberIdAndKeyPlanId(memberId, planId);
        return count > 0;
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
