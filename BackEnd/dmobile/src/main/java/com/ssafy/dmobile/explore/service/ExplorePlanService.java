package com.ssafy.dmobile.explore.service;

import com.ssafy.dmobile.explore.entity.ExplorePlan;
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
