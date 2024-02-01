package com.ssafy.dmobile.member.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberEditDto {

    @JsonProperty("user_nickname")
    private String userNickname;
    @JsonProperty("user_age")
    private Integer userAge;
    @JsonProperty("user_gender")
    private String userGender;
}
