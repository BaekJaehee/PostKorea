package com.ssafy.dmobile.achievements.entity.visit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberAchieveResponseDto {

    @JsonProperty("achieve_id")
    private Long achieveId;
    @JsonProperty("achieve_name")
    private String achieveName;
    @JsonProperty("achieve_desc")
    private String achieveDesc;
    @JsonProperty("achieve_date")
    private Long achieveDate;
    @JsonProperty("achieve_relic_name")
    private String achieveRelicName;
}
