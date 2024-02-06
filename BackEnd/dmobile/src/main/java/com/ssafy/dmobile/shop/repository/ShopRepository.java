package com.ssafy.dmobile.shop.repository;

import com.ssafy.dmobile.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
