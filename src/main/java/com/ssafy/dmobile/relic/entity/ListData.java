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
    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;

    @Column(name = "no")
    private Long no;
    @Column(name = "ccma_name")
    private String ccmaName;
    @Column(name = "ccba_mnm1")
    private String ccbaMnm1;
    @Column(name = "ccba_mnm2")
    private String ccbaMnm2;
    @Column(name = "ccba_kdcd")
    private String ccbaKdcd;
    @Column(name = "ccba_ctcd")
    private String ccbaCtcd;
    @Column(name = "ccba_asno")
    private String ccbaAsno;
//
//    @OneToOne(mappedBy = "listData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private DetailData detailData;

}
