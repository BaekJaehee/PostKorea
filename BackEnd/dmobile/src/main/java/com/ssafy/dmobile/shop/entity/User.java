package com.ssafy.dmobile.Shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;    // long이면 db를 bigint로 바꾸려는데 지금 fk 설정돼있어서 오류발생

    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_pwd")
    private String userPwd;
    @Column(name = "user_gender")
    private String userGender;
    @Column(name = "user_age")
    private int userAge;
    @Column(name = "user_profile_url")
    private String userProfileUrl;
    @Column(name = "is_kakao")
    private boolean isKakao;
    @Column(name = "is_activated")
    private boolean isActivated;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "user_point")
    private int userPoint; // 기본값 0, null X
    @Column(name = "user_role")
    private boolean userRole;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_nickname")
    private String userNickname;
}
