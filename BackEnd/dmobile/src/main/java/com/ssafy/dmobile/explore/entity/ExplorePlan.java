package com.ssafy.dmobile.explore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Entity
@Data
@Table(name = "explore_plan")
public class ExplorePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_name", nullable = false, length = 225)
    private String planName;

    @Column(name = "plan_start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date planStartDate;

    @Column(name = "plan_end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date planEndDate;

    @Column(name = "plan_condition", nullable = false)
    private Boolean planCondition;
}
