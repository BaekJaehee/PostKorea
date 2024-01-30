package com.ssafy.dmobile.entity;

import jakarta.persistence.*;
import lombok.Data;
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
    private DetailData detailData;
}
