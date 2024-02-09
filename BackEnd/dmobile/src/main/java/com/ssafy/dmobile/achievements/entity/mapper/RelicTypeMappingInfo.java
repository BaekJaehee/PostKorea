package com.ssafy.dmobile.achievements.entity.mapper;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelicTypeMappingInfo {

    GUKBO("11", new int[] {69, 70, 71, 72}),
    BOMUL("12", new int[] {73, 74, 75, 76}),
    SAJEOK("13", new int[] {77, 78, 79, 80}),
    MYEONGSUNG("15", new int[] {81, 82, 83, 84}),
    CHEONYEON("16", new int[] {85, 86, 87, 88}),
    MUHYEONG("17", new int[] {89, 90, 91, 92}),
    MINSOK("18", new int[] {93, 94, 95, 96})
    ;

    private final String relicTypeCode;
    private final int[] achieveIds;

    public static RelicTypeMappingInfo findByRelicTypeCode(String relicTypeCode) {

        for (RelicTypeMappingInfo mapping : values()) {
            if (mapping.getRelicTypeCode().equals(relicTypeCode)) {
                return mapping;
            }
        }

        throw new CustomException(ExceptionType.MAPPING_INFO_NOT_FOUND_EXCEPTION);
    }
}
