package com.ssafy.dmobile.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_image")
    private String productImage;
    @Column(name = "product_point")
    private Integer productPoint;

    // 무한재귀 막으려면 단방향
//    @OneToMany(mappedBy = "shop")
//    private List<ShopMember> shopMember;
}
