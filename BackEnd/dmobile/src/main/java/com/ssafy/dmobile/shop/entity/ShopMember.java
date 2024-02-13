package com.ssafy.dmobile.shop.entity;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shop_member")
public class ShopMember {
    @EmbeddedId
    private ShopMemberId shopMemberId;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "shop_member_id")
//    private Long id;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Shop shop;

    @Column(name = "product_date")
    private Long productDate;

    @Column(name = "product_acquisition")
    private String productAcquisition;
}
