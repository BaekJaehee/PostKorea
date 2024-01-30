package com.ssafy.dmobile.service;

import com.ssafy.dmobile.repository.ProductRepository;
import com.ssafy.dmobile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

//    public boolean purchaseProduct(Integer user_id, Long product_id) {
//
//    }
}
