package com.ssafy.dmobile.shop.service;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.member.service.MemberService;
import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

//    public boolean purchaseProduct(Long memberId, Long productId) {
//        // 현재 로그인한 유저 정보 가져오기
//        Member loggedInMember = memberService.getMemberById(memberId);
//
//        if (loggedInMember == null || !loggedInMember.getMemberId().equals(memberId)) {
//            // 유저 정보가 맞지 않으면 권한이 없음을 반환
//            return false;
//        }


        public boolean purchaseProduct (Long id, Long productId){
            Member loggedInMember = memberService.getMemberById(id);

//            if (loggedInMember == null || !loggedInMember.getId().equals(id)) {
//                // 유저 정보가 맞지 않으면 권한이 없음을 반환
//                return false;
//            }

            Shop product = shopRepository.findById(productId).orElse(null);

            if (product != null && loggedInMember.getPoint() >= product.getProductPoint()) {
                // 포인트 공제
                loggedInMember.setPoint(loggedInMember.getPoint() - product.getProductPoint());
                // 상태 저장
                memberRepository.save(loggedInMember);
                return true;
            }
            // 실패
            return false;
        }

    public Shop getProductById(Long productId) {
            return shopRepository.getById(productId);
    }

    public List<Shop> getAllProducts() {
        return shopRepository.findAll();
    }
}