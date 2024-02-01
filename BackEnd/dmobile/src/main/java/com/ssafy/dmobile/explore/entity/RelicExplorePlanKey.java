package com.ssafy.dmobile.explore.entity;

import java.io.Serializable;

// 테이블에 id값이 없으므로 3가지 값을 조합한 복합키를 unique한 id값으로 함
public class RelicExplorePlanKey implements Serializable {
    private Long planId;
    private Long relicId;
    private Long userId;
}
