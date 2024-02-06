package com.ssafy.dmobile.member.entity;

import com.ssafy.dmobile.oauth.service.OAuthType;
import com.ssafy.dmobile.shop.entity.ShopMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "member_email")
    private String email;
    @Column(name = "member_pwd")
    private String password;
    @Column(name = "member_name")
    private String name;
    @Column(name = "member_nickname")
    private String nickname;
    @Column(name = "member_age")
    private Integer age;
    @Column(name = "member_profile_url")
    private String profileUrl;
    @Column(name = "member_point")
    private Integer point = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_gender")
    private MemberGenderType gender;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "member_role")
    private MemberRoleType memberRoleType = MemberRoleType.MEMBER;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "member_oauth_info")
    private OAuthType oAuthType = OAuthType.NONE;

    @OneToMany(mappedBy = "member")
    private List<ShopMember> shopMember;
}
