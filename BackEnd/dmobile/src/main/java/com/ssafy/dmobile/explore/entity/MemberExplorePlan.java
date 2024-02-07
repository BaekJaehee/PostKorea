package com.ssafy.dmobile.explore.entity;

import com.ssafy.dmobile.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "member_explore_plan")
public class MemberExplorePlan {

    @EmbeddedId
    private MemberExplorePlanKey key;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @MapsId("planId")
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private ExplorePlan explorePlan;
}
