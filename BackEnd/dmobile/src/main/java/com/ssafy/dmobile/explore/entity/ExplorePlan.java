package com.ssafy.dmobile.explore.entity;

import com.ssafy.dmobile.member.entity.Member;
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

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "plan_start_date", nullable = false)
    private Long planStartDate;

    @Column(name = "plan_end_date", nullable = false)
    private Long planEndDate;

    @Column(name = "plan_condition", nullable = false)
    private Boolean planCondition;
}
