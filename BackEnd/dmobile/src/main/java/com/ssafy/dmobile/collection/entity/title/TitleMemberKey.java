package com.ssafy.dmobile.collection.entity.title;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class TitleMemberKey implements Serializable {

    private Long memberId;
    private Long titleId;
}