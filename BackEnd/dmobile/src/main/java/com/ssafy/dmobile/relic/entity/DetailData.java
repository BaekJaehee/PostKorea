package com.ssafy.dmobile.relic.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "relic_detail")
public class DetailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relic_id")
    @Schema(description = "식별자")
    private Long relicId;

    @Column(name = "item_id")   // FK
    @Schema(description = "아이템 식별자(외래키)")
    private Long itemId;

    @Column(name = "ccma_name")
    @Schema(description = "문화재종목")
    private String ccmaName;
    @Column(name = "ccba_mnm1")
    @Schema(description = "문화재명(국문)")
    private String ccbaMnm1;
    @Column(name = "ccba_mnm2")
    @Schema(description = "문화재명(한문)")
    private String ccbaMnm2;
    @Column(name = "ccba_kdcd")
    @Schema(description = "종목코드")
    private String ccbaKdcd;
    @Column(name = "ccba_ctcd")
    @Schema(description = "시도코드")
    private String ccbaCtcd;
    @Column(name = "ccba_asno")
    @Schema(description = "관리번호")
    private String ccbaAsno;
    @Column(name = "ccba_cpno")
    @Schema(description = "연계번호")
    private String ccbaCpno;
    @Column(name = "longitude")
    @Schema(description = "위도(0일경우 위치값 없음)")
    private String longitude;
    @Column(name = "latitude")
    @Schema(description = "경도(0일경우 위치값 없음)")
    private String latitude;
    @Column(name = "gcode_name")
    @Schema(description = "문화재분류")
    private String gcodeName;
    @Column(name = "bcode_name")
    @Schema(description = "문화재분류2")
    private String bcodeName;
    @Column(name = "mcode_name")
    @Schema(description = "문화재분류3")
    private String mcodeName;
    @Column(name = "scode_name")
    @Schema(description = "문화재분류4")
    private String scodeName;
    @Column(name = "ccba_quan")
    @Schema(description = "수량")
    private String ccbaQuan;
    @Column(name = "ccba_asdt")
    @Schema(description = "지정일")
    private String ccbaAsdt;
    @Column(name = "ccba_lcad")
    @Schema(description = "소재지 상세")
    private String ccbaLcad;
    @Column(name = "ccce_name")
    @Schema(description = "시대")
    private String ccceName;
    @Column(name = "ccba_poss")
    @Schema(description = "소유자")
    private String ccbaPoss;
    @Column(name = "ccba_admin")
    @Schema(description = "관리자")
    private String ccbaAdmin;
    @Column(name = "image_url")
    @Schema(description = "메인 노출이미지 url")
    private String imageUrl;
    @Column(name = "content", columnDefinition = "LONGTEXT")
    @Schema(description = "설명")
    private String content;
    @Column(name = "region1")
    @Schema(description = "시도")
    private String region1;
    @Column(name = "region2")
    @Schema(description = "시군구")
    private String region2;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
//    private ListData listData;

}
