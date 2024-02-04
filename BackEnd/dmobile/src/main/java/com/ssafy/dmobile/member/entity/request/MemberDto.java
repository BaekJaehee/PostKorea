package com.ssafy.dmobile.member.entity.request;

import com.ssafy.dmobile.member.entity.MemberGenderType;
import com.ssafy.dmobile.member.entity.MemberRoleType;
import com.ssafy.dmobile.oauth.service.OAuthType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {

    @JsonProperty("member_email")
    @Schema(description = "사용자의 고유 식별자")
    private String memberEmail;
    @JsonProperty("member_pwd")
    private String memberPwd;
    @JsonProperty("member_name")
    private String memberName;
    @JsonProperty("member_nickname")
    private String memberNickname;
    @JsonProperty("member_age")
    private Integer memberAge;
    @JsonProperty("member_gender")
    private MemberGenderType memberGender;
    @JsonProperty("member_auth")
    private OAuthType memberAuth;
    @JsonProperty("member_role")
    private MemberRoleType memberRole;
}
