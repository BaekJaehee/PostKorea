package com.ssafy.dmobile.shop.repository;

import com.ssafy.dmobile.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query("SELECT s FROM Shop s WHERE s.isPurchasable = :isPurchasable")
    List<Shop> findByIsPurchasable(Boolean isPurchasable);
}
