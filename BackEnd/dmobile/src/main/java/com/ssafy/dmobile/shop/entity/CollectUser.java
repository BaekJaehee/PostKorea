package com.ssafy.dmobile.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "collect_user")
@Data
public class CollectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collect_user_id")
    private Integer collectUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")   // FK
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "collect_id")    // FK
//    private Collect collect;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
