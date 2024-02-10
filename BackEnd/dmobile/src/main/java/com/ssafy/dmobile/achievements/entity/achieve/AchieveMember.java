package com.ssafy.dmobile.achievements.entity.achieve;

import com.ssafy.dmobile.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "achieve_member")
public class AchieveMember {

    @EmbeddedId
    private AchieveMemberKey key;

    @MapsId("achieveId")
    @ManyToOne
    @JoinColumn(name = "achieve_id")
    private Achieve achieve;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "achieve_date")
    private Long achieveDate;

    @Column(name = "achieve_relic_name")
    private String relicName;
}
