package com.ssafy.dmobile.explore.repository;

import com.ssafy.dmobile.explore.entity.ExplorePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExplorePlanRepository extends JpaRepository<ExplorePlan, Long> {

    @Query("select ep from ExplorePlan ep join MemberExplorePlan mep on ep.planId = mep.explorePlan.planId where mep.member.id = :memberId and ep.planStartDate <= :now and ep.planEndDate >= :now")
    List<ExplorePlan> findOngoingPlans(@Param("memberId") Long memberId, @Param("now") Long now);

    @Query("select ep from ExplorePlan ep join MemberExplorePlan mep on ep.planId = mep.explorePlan.planId where mep.member.id = :memberId and ep.planStartDate > :now")
    List<ExplorePlan> findUpcomingPlans(@Param("memberId") Long memberId, @Param("now") Long now);

    @Query("select ep from ExplorePlan ep join MemberExplorePlan mep on ep.planId = mep.explorePlan.planId where mep.member.id = :memberId and ep.planEndDate < :now")
    List<ExplorePlan> findCompletedPlans(@Param("memberId") Long memberId, @Param("now") Long now);
}
