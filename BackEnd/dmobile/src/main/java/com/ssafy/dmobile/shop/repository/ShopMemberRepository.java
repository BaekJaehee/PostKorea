package com.ssafy.dmobile.shop.repository;

import com.ssafy.dmobile.shop.entity.ShopMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopMemberRepository extends JpaRepository<ShopMember, Long> {
    boolean existsByShopMemberIdMemberIdAndShopMemberIdProductId(Long memberId, Long productId);

    @Query("SELECT sm FROM ShopMember sm WHERE sm.shopMemberId.memberId = :memberId")   // 여기서 필요한 부분(productId, productDate)만 보내기
    List<ShopMember> findByMemberId(Long memberId);
}
