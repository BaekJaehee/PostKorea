package com.ssafy.dmobile.relic.entity;

import com.ssafy.dmobile.relic.entity.DetailData;
import com.ssafy.dmobile.relic.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "tag_relic")
@Getter
public class TagRelic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_relic_id")
    private Integer tagRelicId;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "relic_id")
    private DetailData detailDataList;
}
