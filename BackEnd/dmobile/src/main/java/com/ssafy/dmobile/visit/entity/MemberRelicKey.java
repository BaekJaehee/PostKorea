package com.ssafy.dmobile.visit.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class MemberRelicKey implements Serializable {
    private Long memberId;
    private Long relicId;
}
