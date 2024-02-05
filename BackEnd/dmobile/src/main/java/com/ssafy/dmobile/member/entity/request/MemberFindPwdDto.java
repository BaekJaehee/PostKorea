package com.ssafy.dmobile.member.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberFindPwdDto {

    @JsonProperty("member_email")
    private String memberEmail;
    @JsonProperty("member_name")
    private String memberName;
}