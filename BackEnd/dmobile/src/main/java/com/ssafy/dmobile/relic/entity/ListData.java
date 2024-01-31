package com.ssafy.dmobile.Relic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "relic_list")
public class ListData { // 자바에서는 카멜 케이스로
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Integer listId;

    @Column(name = "no")
    private Integer no;
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

}
