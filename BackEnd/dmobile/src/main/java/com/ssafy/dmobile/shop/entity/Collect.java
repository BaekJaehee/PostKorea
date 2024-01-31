package com.ssafy.dmobile.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;


@Entity
@Data
@Table(name = "collect")
public class Collect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collect_id")
    private Integer collectId;

    @Column(name = "collect_url")
    private String collectUrl;
    @Column(name = "collect_name")
    private String collectName;
    @Column(name = "is_collect")
    private Boolean isCollcet;
    @Column(name = "collect_date")
    private Date collectDate;
}
