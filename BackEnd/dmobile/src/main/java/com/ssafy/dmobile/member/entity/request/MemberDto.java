package com.ssafy.dmobile.member.entity.request;

import com.ssafy.dmobile.oauth.service.OAuthInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {

    @JsonProperty("user_email")
    @Schema(description = "사용자의 고유 식별자")
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
