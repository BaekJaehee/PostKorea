package com.ssafy.dmobile.achievements.entity.achieve;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class AchieveMemberKey implements Serializable {

    private Long memberId;
    private Long achieveId;
}