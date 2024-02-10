package com.ssafy.dmobile.shop.entity.dto;

import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.entity.ShopMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopMemberDto {
    private Long productId;
    private String productName;
    private String productImage;
    private Long productDate;
    private String productExplanation;

    // 기본 생성자
    public ShopMemberDto() {
    }

    // 매개변수를 받는 생성자
    public ShopMemberDto(Long productId, String productName, String productImage, Long productDate, String productExplanation) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
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
        dto.setProductName(shopMember.getShop().getProductName());
        dto.setProductImage(shopMember.getShop().getProductImage());
        dto.setProductDate(shopMember.getProductDate());
        dto.setProductExplanation(shopMember.getShop().getProductExplanation());
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
        dto.setProductName(shop.getProductName());
        dto.setProductImage(shop.getProductImage());
        dto.setProductExplanation(shop.getProductExplanation());
        return dto;
    }



//    public static ShopMemberDto from(ShopMember shopMember) {
//        ShopMemberDto dto = new ShopMemberDto();
//        dto.setProductId(shopMember.getShop().getProductId());
//        dto.setProductDate(shopMember.getProductDate());
//        return dto;
//    }
}
