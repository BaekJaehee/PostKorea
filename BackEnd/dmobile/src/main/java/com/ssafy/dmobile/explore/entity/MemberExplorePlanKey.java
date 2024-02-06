package com.ssafy.dmobile.explore.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MemberExplorePlanKey implements Serializable {

    private Long memberId;
    private Long planId;
}
