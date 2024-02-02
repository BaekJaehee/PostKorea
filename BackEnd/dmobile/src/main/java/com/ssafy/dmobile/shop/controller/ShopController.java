package com.ssafy.dmobile.shop.controller;

import com.ssafy.dmobile.shop.entity.Shop;
import com.ssafy.dmobile.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopRepository shopRepository;

    @GetMapping("/product")    // 상점 들어가면 맨 처음 뜨는 물품 리스트
    public ResponseEntity<List<Shop>> productList() {
        List<Shop> shop = shopRepository.findAll();
        return ResponseEntity.ok().body(shop);
    }
}