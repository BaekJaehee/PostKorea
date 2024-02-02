package com.ssafy.dmobile.relic.mapping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionMapping {    // '경북'과 '경상북도'처럼 지역값을 동일하게 취급하기 위한 클래스
    private String dbValue; // db에 저장된 값
    private String displayValue; // 화면에 표시될 값

    public RegionMapping(String dbValue, String displayValue) {
        this.dbValue = dbValue;
        this.displayValue = displayValue;
    }
}
