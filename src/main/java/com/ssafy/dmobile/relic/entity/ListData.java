package com.ssafy.dmobile.relic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "relic_item")
public class ListData { // 자바에서는 카멜 케이스로
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    @Schema(description = "아이템 고유 식별자")
    private Long itemId;

    @Column(name = "no")
    @Schema(description = "고유 키값")
    private Long no;
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
}
