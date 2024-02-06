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

//    public Shop getProductById(Long productId) {
//            return shopRepository.getById(productId);
//    }
    // 상품 구매 로직
//        Optional<Member> optionalMember = memberRepository.findById(memberId);
//        Optional<Shop> optionalShop = shopRepository.findById(productId);
//
//        if (optionalMember.isPresent() && optionalShop.isPresent()) {
//            Member member = optionalMember.get();
//            Shop product = optionalShop.get();
//
//            // 포인트 차감 (예시로 10 포인트 차감)
//            int pointsToDeduct = 10;
//            if (member.getPoint() >= pointsToDeduct) {
//                member.setPoint(member.getPoint() - pointsToDeduct);
//            } else {
//                // 포인트 부족으로 구매 실패
//                return false;
//            }
//
//            // 상품을 회원의 컬렉션에 추가
//            member.getCollectedProducts().add(product);
//
//            // 저장된 상품 삭제
//            shopRepository.delete(product);
//
//            // 회원 정보 업데이트
//            memberRepository.save(member);
//
//            return true; // 구매 성공
//        }
//
//        return false; // 회원 또는 상품이 존재하지 않음
//    }
}