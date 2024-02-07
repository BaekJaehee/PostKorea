package com.ssafy.dmobile.member.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginRequestDto {

    @JsonProperty("member_email")
    private String memberEmail;
    @JsonProperty("member_pwd")
    private String memberPwd;
}
