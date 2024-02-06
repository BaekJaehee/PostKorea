package com.ssafy.dmobile.relic.entity;

import com.ssafy.dmobile.theme.entity.ThemeRelic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "relic_detail")
public class DetailData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relic_id")
    @Schema(description = "문화재 상세 식별자")
//    @OneToMany(mappedBy = "themerelicrelation")
    private Long relicId;

    @OneToMany(mappedBy = "detailData")
    private Set<ThemeRelic> themeRelics = new HashSet<>();

    @Column(name = "item_id")   // FK
    @Schema(description = "아이템 고유 식별자")
    private Long itemId;

    @Column(name = "ccma_name")
    @Schema(description = "문화재 종목")
    private String ccmaName;
    @Column(name = "ccba_mnm1")
    @Schema(description = "문화재명(국문)")
    private String ccbaMnm1;
    @Column(name = "ccba_mnm2")
    @Schema(description = "문화재명(한문)")
    private String ccbaMnm2;
    @Column(name = "ccba_kdcd")
    @Schema(description = "종목 코드")
    private String ccbaKdcd;
    @Column(name = "ccba_ctcd")
    @Schema(description = "시도 코드")
    private String ccbaCtcd;
    @Column(name = "ccba_asno")
    @Schema(description = "관리 번호")
    private String ccbaAsno;
    @Column(name = "ccba_cpno")
    @Schema(description = "문화재 연계번호")
    private String ccbaCpno;
    @Column(name = "longitude")
    @Schema(description = "경도(0일 경우 위치 값 없음)")
    private String longitude;
    @Column(name = "latitude")
    @Schema(description = "위도(0일 경우 위치 값 없음)")
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
    @Schema(description = "지정(등록일)")
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
    @Schema(description = "메인 노출 이미지 URL")
    private String imageUrl;
    @Column(name = "content", columnDefinition = "LONGTEXT")
    @Schema(description = "내용")
    private String content;
    @Column(name = "region1")
    @Schema(description = "시/도")
    private String region1;
    @Column(name = "region2")
    @Schema(description = "시/군/구")
    private String region2;
}
