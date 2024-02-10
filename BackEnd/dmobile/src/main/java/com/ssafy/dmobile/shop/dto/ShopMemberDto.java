package com.ssafy.dmobile.shop.dto;

import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.entity.ShopMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
public class ShopMemberDto {
    private Long productId;
    private Long productDate;
    private String productExplanation;

    // 기본 생성자
    public ShopMemberDto() {
    }

    // 매개변수를 받는 생성자
    public ShopMemberDto(Long productId, Long productDate, String productExplanation) {
        this.productId = productId;
        this.productDate = productDate;
        this.productExplanation = productExplanation;
    }

//    public static ShopMemberDto mapFromShopMember(ShopMember shopMember) {
//        return new ShopMemberDto(shopMember.getShop().getProductId(), shopMember.getProductDate());
//    }

    // mapFromShopMember 메서드 수정하여 상품 설명을 ShopMemberDto로 매핑
    public static ShopMemberDto mapFromShopMember(ShopMember shopMember) {
        ShopMemberDto dto = new ShopMemberDto();
        dto.setProductId(shopMember.getShop().getProductId());
        dto.setProductDate(shopMember.getProductDate());
        dto.setProductExplanation(shopMember.getShop().getProductExplanation()); // 상품 설명 설정
        return dto;
    }

//    public static ShopMemberDto mapFromShop(Shop shop) {
//        ShopMemberDto dto = new ShopMemberDto(shop.getProductId(), null); // productDate는 Shop 엔티티에 없으니까 null
//        // 어차피 구매 안 한 것
//        return dto;
//    }

    // productExplanation 추가
    public static ShopMemberDto mapFromShop(Shop shop) {
        ShopMemberDto dto = new ShopMemberDto();
        dto.setProductId(shop.getProductId());
        dto.setProductExplanation(shop.getProductExplanation()); // 상품 설명
        return dto;
    }



//    public static ShopMemberDto from(ShopMember shopMember) {
//        ShopMemberDto dto = new ShopMemberDto();
//        dto.setProductId(shopMember.getShop().getProductId());
//        dto.setProductDate(shopMember.getProductDate());
//        return dto;
//    }
}
