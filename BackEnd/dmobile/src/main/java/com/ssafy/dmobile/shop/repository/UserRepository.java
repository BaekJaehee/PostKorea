package com.ssafy.dmobile.Shop.repository;

import com.ssafy.dmobile.Shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
