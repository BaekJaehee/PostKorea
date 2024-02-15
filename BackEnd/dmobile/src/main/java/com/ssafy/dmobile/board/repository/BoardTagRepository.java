package com.ssafy.dmobile.board.repository;

import com.ssafy.dmobile.board.entity.BoardTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long>, CustomBoardTagRepository {
    List<BoardTag> findByTag_TagName(String tagName, Pageable pageable);
}
