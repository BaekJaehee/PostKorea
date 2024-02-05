package com.ssafy.dmobile.collection.entity.achievement;

import com.ssafy.dmobile.member.entity.Member;
import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

@Entity
public class MemberAchieve {

    @EmbeddedId
    private MemberAchieveKey key;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("achieveId")
    @ManyToOne
    @JoinColumn(name = "achieve_id")
    private Achieve achieve;

    @Column(name = "achieve_date")
    private Long achieveDate;
}
