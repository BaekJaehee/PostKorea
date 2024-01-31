package com.ssafy.dmobile.shop.repository;

import com.ssafy.dmobile.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
