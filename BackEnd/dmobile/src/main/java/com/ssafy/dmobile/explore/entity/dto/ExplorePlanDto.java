package com.ssafy.dmobile.explore.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExplorePlanDto {

    @JsonProperty("plan_name")
    private String planName;

    @JsonProperty("plan_start_date")
    private Long planStartDate;

    @JsonProperty("plan_end_date")
    private Long planEndDate;

    @JsonProperty("plan_condition")
    private Boolean planCondition;
}
