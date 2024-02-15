package com.ssafy.dmobile.shop.controller;

import com.ssafy.dmobile.common.exception.CustomException;
import com.ssafy.dmobile.common.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.shop.entity.dto.ShopMemberDto;
import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.entity.ShopMember;
import com.ssafy.dmobile.shop.entity.ShopMemberId;
//import com.ssafy.dmobile.shop.mapper.ShopMemberMapper;
import com.ssafy.dmobile.shop.repository.ShopMemberRepository;
import com.ssafy.dmobile.shop.repository.ShopRepository;
import com.ssafy.dmobile.member.service.MemberService;
import com.ssafy.dmobile.shop.service.ShopMemberService;
import com.ssafy.dmobile.shop.service.ShopService;
import com.ssafy.dmobile.common.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final ShopService shopService;
    private final ShopMemberService shopMemberService;
    private final ShopMemberRepository shopMemberRepository;
    private final MemberService memberService;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping("/product")    // 상점 들어가면 맨 처음 뜨는 물품 리스트
    @Operation(summary = "물건 전체 리스트")
    public ResponseEntity<List<Shop>> productList() {
        List<Shop> shop = shopRepository.findAll();
        return ResponseEntity.ok().body(shop);
    }

    @GetMapping("/product/available")
    @Operation(summary = "상점에서 구매 가능한 물건만 표시")
    public ResponseEntity<?> availableOnShop() {
        List<Shop> shop = shopRepository.findByIsPurchasable(true);
        return ResponseEntity.ok().body(shop);
    }

    @GetMapping("/product/unavailable")
    @Operation(summary = "상점에서는 구매 불가능하고 업적이나 게임 보상으로 얻을 수 있는 물건만 표시")
    public ResponseEntity<?> unavailableOnShop() {
        List<Shop> shop = shopRepository.findByIsPurchasable(false);
        return ResponseEntity.ok().body(shop);
    }

    // swagger에서 로그인 후 동작 검사 필요(application.properties에서 jwt.secret-key는 사용자의 토큰이 아님)
    // 특정 물품을 구매했을 때(목록에서 구매버튼을 누르면 동작)
    @PostMapping("/purchase/{productId}")
    @Operation(summary = "물건 구입", description = "productId에 해당하는 물건의 구입 진행<br>" +
        "사용자의 포인트가 모자란 경우, 혹은 중복 구매 시 메시지")
    @SecurityRequirement(name="Authorization")
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

        // 상점에서만 구매가 가능한 것인지 구매 가능 여부 확인
//        Shop shop = shopService.getProductById(productId);
//        if (!shop.getIsPurchasable()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This item is not available for purchase in Shop");
//        }


        // 중복 구매 확인
        boolean isDuplicatePurchase = shopMemberService.existsByMemberIdAndProductId(memberId, productId);

        if (isDuplicatePurchase) {
            // 중복 구매인 경우
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Duplicate purchase is not allowed");
            throw new CustomException(ExceptionType.PRODUCT_DUPLICATE_EXCEPTION);
        }
        // 구매 True False (member_id, product_id)
        boolean purchaseResult = shopService.purchaseProduct(memberId, productId);

        try {
            // 무한재귀 발생 -> dto 생성
            if (purchaseResult) {   // 정상적으로 구매 성공
                // db에서 물품 삭제는 안함
                // dto에 추가
                // 단방향으로 바꾸니까 dto 안써도됨

                ShopMember shopMember = new ShopMember();
                Member member = memberService.getMemberById(memberId);
                 Shop shop = shopService.getProductById(productId);
                // 구매가능여부 확인할 때 이미 나옴

                // ShopMemberId 객체 생성
                ShopMemberId shopMemberId = new ShopMemberId();
                shopMemberId.setMemberId(memberId);
                shopMemberId.setProductId(productId);

                shopMember.setShopMemberId(shopMemberId);
                shopMember.setMember(member);
                shopMember.setShop(shop);

                // 현재 시간을 millisecond 단위로 가져와서 productDate로 설정
                long currentTimeMillis = System.currentTimeMillis();
                shopMember.setProductDate(currentTimeMillis);

                shopMemberService.saveShopMember(shopMember);

                return ResponseEntity.ok().body(String.format("%s 상품 구매 완료", productId));

            } else {
//                List<Shop> product = shopRepository.findByIsPurchasable(true);
//                return ResponseEntity.ok().body(product);
//                return ResponseEntity.ok().body("No enough point");
//                throw new CustomException(ExceptionType.PRODUCT_POINT_EXCEPTION);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("포인트가 부족합니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/collect")
    @Operation(summary = "구매 물건 확인", description = "특정 유저가 구입한 물건과 구입하지 않은 물건을 모아서 확인<br>" +
        "구입 여부는 productDate로 구분(구입했다면 날짜 표시, 아니라면 null)<br>" +
        "구입한 물건들의 리스트가 상위에 오고 하위에는 구입하지 않은 물건 리스트")
    @SecurityRequirement(name="Authorization")
    public ResponseEntity<?> bought(@RequestHeader("Authorization") String token) {
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

        // 특정 유저가 구입한 물건 조회
        List<ShopMember> purchasedItems = shopMemberService.getPurchasedItemsByMemberId(memberId);

        // 모든 상품 조회
        List<Shop> allItems = shopService.getAllProducts();

        // 유저가 구입하지 않은 상품 Shop에서 가져옴
        List<Shop> nonPurchasedItems = allItems.stream()
                .filter(item -> purchasedItems.stream()
                        .noneMatch(purchasedItem -> purchasedItem.getShop().getProductId().equals(item.getProductId())))
                .collect(Collectors.toList());

        // Shop 객체를 ShopMemberDto로 변환
        List<ShopMemberDto> purchasedDtoList = purchasedItems.stream()
                .map(ShopMemberDto::mapFromShopMember)
                .collect(Collectors.toList());
        List<ShopMemberDto> nonPurchasedDtoList = nonPurchasedItems.stream()
                .map(ShopMemberDto::mapFromShop)
                .collect(Collectors.toList());

        // 구입한 상품과 구입하지 않은 상품을 합쳐서 반환
        List<ShopMemberDto> mergedDtoList = new ArrayList<>();
        mergedDtoList.addAll(purchasedDtoList);
        mergedDtoList.addAll(nonPurchasedDtoList);

        return ResponseEntity.ok().body(mergedDtoList);
    }
}