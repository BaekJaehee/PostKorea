package com.ssafy.dmobile.Board.repository;

import com.ssafy.dmobile.Board.entity.BoardTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    List<BoardTag> findByTag_TagName(String tagName, Pageable pageable);
}
