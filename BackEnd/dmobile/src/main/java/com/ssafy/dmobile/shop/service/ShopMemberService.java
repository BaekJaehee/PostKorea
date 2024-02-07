package com.ssafy.dmobile.shop.service;

import com.ssafy.dmobile.shop.dto.ShopMemberDto;
import com.ssafy.dmobile.shop.entity.ShopMember;
//import com.ssafy.dmobile.shop.mapper.ShopMemberMapper;
import com.ssafy.dmobile.shop.repository.ShopMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopMemberService {
    private final ShopMemberRepository shopMemberRepository;

    public void saveShopMember(ShopMember shopMember) {
        shopMemberRepository.save(shopMember);
    }

    public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        return shopMemberRepository.existsByShopMemberIdMemberIdAndShopMemberIdProductId(memberId, productId);
    }

//    public List<ShopMemberDto> getShopMembers() {
//        List<ShopMember> shopMembers = shopMemberRepository.findAll();
//        return shopMemberMapper.toDtoList(shopMembers);
//    }

    public List<ShopMember> getPurchasedItemsByMemberId(Long memberId) {
        return shopMemberRepository.findByMemberId(memberId);
    }
}
