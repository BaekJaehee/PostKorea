package com.ssafy.dmobile.explore.repository;

import com.ssafy.dmobile.explore.entity.ExplorePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ExplorePlanRepository extends JpaRepository<ExplorePlan, Long> {

    @Query("select e from ExplorePlan e where e.memberId = :id and e.planStartDate <= :now and e.planEndDate >= :now")
    List<ExplorePlan> findOngoingPlans(Long id, Date now);
    @Query("select e from ExplorePlan e where e.memberId = :id and e.planStartDate > :now")
    List<ExplorePlan> findUpcomingPlans(Long id, Date now);
    @Query("select e from ExplorePlan e where e.memberId = :id and e.planEndDate < :now")
    List<ExplorePlan> findCompletedPlans(Long id, Date now);
}
