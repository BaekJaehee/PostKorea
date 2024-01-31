package com.ssafy.dmobile.Shop.repository;

import com.ssafy.dmobile.Shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
