package com.ssafy.dmobile.repository;

import com.ssafy.dmobile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
