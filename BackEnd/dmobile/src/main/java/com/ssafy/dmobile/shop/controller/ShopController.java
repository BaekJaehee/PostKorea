package com.ssafy.dmobile.shop.controller;

import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.shop.dto.ShopMemberDto;
import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.entity.ShopMember;
import com.ssafy.dmobile.shop.repository.ShopRepository;
import com.ssafy.dmobile.member.service.MemberService;
import com.ssafy.dmobile.shop.service.ShopMemberService;
import com.ssafy.dmobile.shop.service.ShopService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final ShopService shopService;
    private final ShopMemberService shopMemberService;
    private final MemberService memberService;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping("/product")    // 상점 들어가면 맨 처음 뜨는 물품 리스트
    public ResponseEntity<List<Shop>> productList() {
        List<Shop> shop = shopRepository.findAll();
        return ResponseEntity.ok().body(shop);
    }

    // swagger에서 로그인 후 동작 검사 필요(application.properties에서 jwt.secret-key는 사용자의 토큰이 아님)
    // 특정 물품을 구매했을 때(목록에서 구매버튼을 누르면 동작)
    @GetMapping("/purchase/{productId}")
    @Operation(summary = "물건 구입", description = "productId에 해당하는 물건의 구입 진행. 같은 물건 중복 구매 방지 기능 필요")
    public ResponseEntity<?> PurchaseProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Long productId) {
        // 물품을 구매하는 유저가 현재 로그인한 유저여야 함 -> 토큰으로 구분

        // 컨트롤러에서 id 추출하고 extractMemberId
        // 토큰 -> id 변환
        // service에 id 넘기기

        // 토큰에서 memberId 추출
        Long memberId= authTokensGenerator.extractMemberId(token);

        // 현재 로그인한 유저 정보 가져오기
        Member loggedInMember = memberService.getMemberById(memberId);

        // 로그인이 되어있지 않은 경우 또는 유저 정보가 존재하지 않는 경우
        if (loggedInMember == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", "unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 구매 True False (member_id, product_id)
        boolean purchaseResult = shopService.purchaseProduct(memberId, productId);

        try {
            // 무한재귀 발생 -> dto 생성
            if (purchaseResult) {   // 정상적으로 구매 성공
                // db에서 물품 삭제는 안함
                // dto에 추가
                ShopMemberDto shopMemberDto = new ShopMemberDto();
                shopMemberDto.setMemberId(memberId);
                shopMemberDto.setProductId(productId);
                shopMemberDto.setProductDate(null);

                ShopMember shopMember = convertDtoToEntity(shopMemberDto);  // dto 엔터티로 변경
                shopMemberService.saveShopMember(shopMember);   // 저장

                return ResponseEntity.ok().body(shopMember);

//                return ResponseEntity.ok().body(shopMemberDto);

//                ShopMember shopMember = new ShopMember();
//                Member member = memberService.getMemberById(memberId);
//                System.out.println("MEMBER: "+member+"★★★★★★★★★");
//                shopMember.setMember(member);
//                Shop shop = shopService.getProductById(productId);
//                shopMember.setShop(shop);
                // shopMember.setProductDate();

                // ShopMemberId객체 생성
//                ShopMemberId shopMemberId = new ShopMemberId(member.getId(), shop.getProductId());
//
//                ShopMember shopMember = new ShopMember();
//                shopMember.setShopMemberId(shopMemberId);
//                shopMember.setMember(member);
//                shopMember.setShop(shop);
//                shopMemberService.saveShopMember(shopMember);

//                return ResponseEntity.ok().body(shopMember);
//
//                // db에 남은 물품들 가져오기
//                List<Shop> remainProduct = shopRepository.findAll();
//                return ResponseEntity.ok().body(remainProduct);
            } else {
                List<Shop> product = shopRepository.findAll();
                return ResponseEntity.ok().body(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    private ShopMember convertDtoToEntity(ShopMemberDto shopMemberDto) {
        ShopMember shopMember = new ShopMember();
        Member member = memberRepository.findById(shopMemberDto.getMemberId()).orElse(null);
        if (member == null) {
            // 회원이 없을 경우 예외 처리
            throw new IllegalArgumentException("Member not found with ID: " + shopMemberDto.getMemberId());
        }
        shopMember.setMember(member);
        Shop shop = shopRepository.findById(shopMemberDto.getProductId()).orElse(null);
        if (shop == null) {
            // 상품이 없을 경우 예외 처리
            throw new IllegalArgumentException("Shop not found with ID: " + shopMemberDto.getProductId());
        }
        shopMember.setShop(shop);
        shopMember.setProductDate(shopMemberDto.getProductDate());
        return shopMember;
    }
}