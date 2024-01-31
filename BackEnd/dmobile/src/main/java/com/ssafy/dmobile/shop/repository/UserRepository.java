package com.ssafy.dmobile.shop.repository;

import com.ssafy.dmobile.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
