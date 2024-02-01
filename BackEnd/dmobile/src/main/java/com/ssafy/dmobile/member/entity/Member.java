package com.ssafy.dmobile.member.entity;

import com.ssafy.dmobile.oauth.service.OAuthInfo;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Getter
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
    @Column(name = "member_id")
    private String email;
    @Column(name = "member_password")
    private String password;
    @Column(name = "member_name")
    private String name;
    @Column(name = "member_nickname")
    private String nickname;
    @Column(name = "member_age")
    private Integer age;
    @Column(name = "member_gender")
    private String gender;
    @Column(name = "member_profile_url")
    private String profileUrl;
    @Column(name = "member_point")
    private Integer point = 0;
    @Column(name = "member_auth")
    private OAuthInfo oAuthInfo;
}
