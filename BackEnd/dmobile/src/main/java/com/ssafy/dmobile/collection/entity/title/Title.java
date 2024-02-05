package com.ssafy.dmobile.collection.entity.title;

import jakarta.persistence.*;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

@Entity
@Data
@Table(name = "title")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id")
    private Long titleId;

    @Column(name = "title_name")
    private String titleName;
    @Column(name = "title_desc")
    private String titleDesc;
}
