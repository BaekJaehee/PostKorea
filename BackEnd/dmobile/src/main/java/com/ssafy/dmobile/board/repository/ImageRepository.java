package com.ssafy.dmobile.board.repository;

import com.ssafy.dmobile.board.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // 이미지
}
