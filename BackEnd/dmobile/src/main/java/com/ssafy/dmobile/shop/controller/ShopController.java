package com.ssafy.dmobile.shop.controller;

import com.ssafy.dmobile.shop.entity.Product;
import com.ssafy.dmobile.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")    // 상점 들어가면 맨 처음 뜨는 물품 리스트
    public ResponseEntity<List<Product>> productList() {
        List<Product> product = productRepository.findAll();
        return ResponseEntity.ok().body(product);
    }
}