package com.ssafy.dmobile.member.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberEditDto {

    @JsonProperty("member_nickname")
    private String memberNickname;
    @JsonProperty("member_age")
    private Integer memberAge;
}
