package com.ssafy.dmobile.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopMemberDto {
//    private Long id;
    private Long memberId;
    private Long productId;
    private Long productDate;
}
