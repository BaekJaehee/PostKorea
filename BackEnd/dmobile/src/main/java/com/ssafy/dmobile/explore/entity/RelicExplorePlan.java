package com.ssafy.dmobile.explore.entity;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.relic.entity.DetailData;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "relic_explore_plan")
public class RelicExplorePlan {

    @EmbeddedId
    private RelicExplorePlanKey key;

    @MapsId("planId")
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private ExplorePlan explorePlan;

    @MapsId("relicId")
    @ManyToOne
    @JoinColumn(name = "relic_id", nullable = false)
    private DetailData detailData;

    @Column(name = "relic_visited", nullable = false)
    private boolean visited;
}
