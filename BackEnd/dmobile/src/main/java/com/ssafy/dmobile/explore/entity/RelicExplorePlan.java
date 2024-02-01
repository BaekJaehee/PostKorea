package com.ssafy.dmobile.explore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "relic_explore_plan")
@IdClass(RelicExplorePlanKey.class)
public class RelicExplorePlan {

    @Id
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Id
    @Column(name = "relic_id", nullable = false)
    private Long relicId;

    @Id
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "visited", nullable = false)
    private boolean visited;
}
