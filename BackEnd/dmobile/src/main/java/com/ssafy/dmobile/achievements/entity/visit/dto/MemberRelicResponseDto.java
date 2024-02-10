package com.ssafy.dmobile.achievements.entity.visit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRelicResponseDto {

    @JsonProperty("relic_id")
    private Long relicId;

    @JsonProperty("relic_name")
    private String ccbaMnm1;

    @JsonProperty("visited_content")
    private String visitedContent;

    @JsonProperty("visited_create_time")
    private Long visitedCreateTime;

    @JsonProperty("visited_image")
    private String visitedImage;

//    @JsonProperty("item_id")
//    @Schema(description = "아이템 고유 식별자")
//    private Long itemId;
//
//    @JsonProperty("ccma_name")
//    @Schema(description = "문화재 종목")
//    private String ccmaName;
//

//
//    @JsonProperty("ccba_mnm2")
//    @Schema(description = "문화재명(한문)")
//    private String ccbaMnm2;
//
//    @JsonProperty("ccba_kdcd")
//    @Schema(description = "종목 코드")
//    private String ccbaKdcd;
//
//    @JsonProperty("ccba_ctcd")
//    @Schema(description = "시도 코드")
//    private String ccbaCtcd;
//
//    @JsonProperty("ccba_asno")
//    @Schema(description = "관리 번호")
//    private String ccbaAsno;
//
//    @JsonProperty("ccba_cpno")
//    @Schema(description = "문화재 연계번호")
//    private String ccbaCpno;
//
//    @JsonProperty("longitude")
//    @Schema(description = "경도(0일 경우 위치 값 없음)")
//    private String longitude;
//
//    @JsonProperty("latitude")
//    @Schema(description = "위도(0일 경우 위치 값 없음)")
//    private String latitude;
//
//    @JsonProperty("gcode_name")
//    @Schema(description = "문화재분류")
//    private String gcodeName;
//
//    @JsonProperty("bcode_name")
//    @Schema(description = "문화재분류2")
//    private String bcodeName;
//
//    @JsonProperty("mcode_name")
//    @Schema(description = "문화재분류3")
//    private String mcodeName;
//
//    @JsonProperty("scode_name")
//    @Schema(description = "문화재분류4")
//    private String scodeName;
//
//    @JsonProperty("ccba_quan")
//    @Schema(description = "수량")
//    private String ccbaQuan;
//
//    @JsonProperty("ccba_asdt")
//    @Schema(description = "지정(등록일)")
//    private String ccbaAsdt;
//
//    @JsonProperty("ccba_lcad")
//    @Schema(description = "소재지 상세")
//    private String ccbaLcad;
//
//    @JsonProperty("ccce_name")
//    @Schema(description = "시대")
//    private String ccceName;
//
//    @JsonProperty("ccba_poss")
//    @Schema(description = "소유자")
//    private String ccbaPoss;
//
//    @JsonProperty("ccba_admin")
//    @Schema(description = "관리자")
//    private String ccbaAdmin;
//
//    @JsonProperty("image_url")
//    @Schema(description = "메인 노출 이미지 URL")
//    private String imageUrl;
//
//    @JsonProperty("content")
//    @Schema(description = "내용")
//    private String content;
//
//    @JsonProperty("region1")
//    @Schema(description = "시/도")
//    private String region1;
//
//    @JsonProperty("region2")
//    @Schema(description = "시/군/구")
//    private String region2;
}
