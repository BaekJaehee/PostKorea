package com.ssafy.dmobile.collection.entity.title;

import com.ssafy.dmobile.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "title_member")
public class TitleMember {

    @EmbeddedId
    private TitleMemberKey key;

    @MapsId("titleId")
    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title_date")
    private Long titleDate;
}
