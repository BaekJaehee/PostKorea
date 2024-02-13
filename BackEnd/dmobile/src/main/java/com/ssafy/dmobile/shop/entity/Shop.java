package com.ssafy.dmobile.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

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
    @Column(name = "product_explanation")
    private String productExplanation;
    @Column(name = "is_purchasable")
    private Boolean isPurchasable;
    @Column(name = "product_acquisition")
    private String productAcquisition;

    // 컨트롤러에서 접근하기 위함
    public boolean getIsPurchasable() {
        return this.isPurchasable;
    }

    // 무한재귀 막으려면 단방향
//    @OneToMany(mappedBy = "shop")
//    private List<ShopMember> shopMember;
}
