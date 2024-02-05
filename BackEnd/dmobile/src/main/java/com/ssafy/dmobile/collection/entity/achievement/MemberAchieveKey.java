package com.ssafy.dmobile.collection.entity.achievement;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class MemberAchieveKey implements Serializable {

    private Long memberId;
    private Long achieveId;
}