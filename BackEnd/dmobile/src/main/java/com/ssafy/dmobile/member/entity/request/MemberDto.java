package com.ssafy.dmobile.member.entity.request;

import com.ssafy.dmobile.oauth.service.OAuthInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {

    @JsonProperty("user_email")
    private String userEmail;
    @JsonProperty("user_pwd")
    private String userPwd;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_nickname")
    private String userNickname;
    @JsonProperty("user_age")
    private Integer userAge;
    @JsonProperty("user_gender")
    private String userGender;
    @JsonProperty("user_auth")
    private OAuthInfo userAuth;
}
