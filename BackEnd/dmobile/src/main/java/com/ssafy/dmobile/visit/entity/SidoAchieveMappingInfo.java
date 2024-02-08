package com.ssafy.dmobile.visit.entity;

import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SidoAchieveMappingInfo {

    SEOUL("11", new int[] {1, 2, 3, 4}),
    BUSAN("21", new int[] {5, 6, 7, 8}),
    DAEGU("22", new int[] {9, 10, 11, 12}),
    INCHEON("23", new int[] {13, 14, 15, 16}),
    GWANGJU("24", new int[] {17, 18, 19, 20}),
    DAEJEON("25", new int[] {21, 22, 23, 24}),
    ULSAN("26", new int[] {25, 26, 27, 28}),
    SEJONG("45", new int[] {29, 30, 31, 32}),
    GYEONGGI("31", new int[] {33, 34, 35, 36}),
    GANGWON("32", new int[] {37, 38, 39, 40}),
    CHUNGBUK("33", new int[] {41, 42, 43, 44}),
    CHUNGNAM("34", new int[] {45, 46, 47, 48}),
    JEONBUK("35", new int[] {49, 50, 51, 52}),
    JEONNAM("36", new int[] {53, 54, 55, 56}),
    GYEONGBUK("37", new int[]{57, 58, 59, 60}),
    GYEONGNAM("38", new int[]{61, 62, 63, 64}),
    JEJU("50", new int[]{65, 66, 67, 68})
    ;

    private final String sidoCode;
    private final int[] achieveIds;

    public static SidoAchieveMappingInfo findBySidoCode(String sidoCode) {

        for (SidoAchieveMappingInfo mapping : values()) {
            if (mapping.getSidoCode().equals(sidoCode)) {
                return mapping;
            }
        }

        throw new CustomException(ExceptionType.MAPPING_INFO_NOT_FOUND_EXCEPTION);
    }
}
