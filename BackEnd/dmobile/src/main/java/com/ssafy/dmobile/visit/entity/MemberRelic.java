package com.ssafy.dmobile.visit.entity;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.relic.entity.DetailData;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "visit")
public class MemberRelic {

    @EmbeddedId
    private MemberRelicKey key;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @MapsId("relicId")
    @JoinColumn(name = "relic_id")
    private DetailData detailData;

    @Column(name = "visited_image")
    private String visitedImage;

    @Column(name = "visited_content")
    private String visitedContent;

    @Column(name = "visited_createtime")
    private Long  visitedCreateTime;

    @Column(name = "visited_comment")
    private String visitedComment;
}