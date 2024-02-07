package com.ssafy.dmobile.member.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.dmobile.member.entity.MemberGenderType;
import com.ssafy.dmobile.member.entity.MemberRoleType;
import com.ssafy.dmobile.oauth.service.OAuthType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponseDto {

    @JsonProperty("member_email")
    private String memberEmail;
    @JsonProperty("member_name")
    private String memberName;
    @JsonProperty("member_nickname")
    private String memberNickname;
    @JsonProperty("member_profile_url")
    private String memberProfileUrl;
    @JsonProperty("member_age")
    private Integer memberAge;
    @JsonProperty("member_point")
    private Integer memberPoint;

    @JsonProperty("member_gender")
    private MemberGenderType memberGender;

    @JsonProperty("member_auth")
    private OAuthType memberAuth;

    @JsonProperty("member_role")
    private MemberRoleType memberRole;
}
