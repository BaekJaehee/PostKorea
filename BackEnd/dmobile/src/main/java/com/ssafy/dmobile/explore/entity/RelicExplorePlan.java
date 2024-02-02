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

    @Column(name = "visited", nullable = false)
    private boolean visited;
}
