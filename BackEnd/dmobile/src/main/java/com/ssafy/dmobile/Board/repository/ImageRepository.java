package com.ssafy.dmobile.Board.repository;

import com.ssafy.dmobile.Board.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // 이미지
}
