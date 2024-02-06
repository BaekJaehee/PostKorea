package com.ssafy.dmobile.shop.service;

import com.ssafy.dmobile.shop.entity.ShopMember;
import com.ssafy.dmobile.shop.repository.ShopMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopMemberService {
    private final ShopMemberRepository shopMemberRepository;

    public void saveShopMember(ShopMember shopMember) {
        shopMemberRepository.save(shopMember);
    }
}
