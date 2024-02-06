package com.ssafy.dmobile.collection.entity.achievement;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "achieve")
public class Achieve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achieve_id")
    private Long achieveId;

    @Column(name = "achieve_name")
    private String achieveName;
    @Column(name = "achieve_desc")
    private String achieveDesc;
}
