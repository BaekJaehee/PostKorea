package com.ssafy.dmobile.board.repository;

import com.ssafy.dmobile.board.entity.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // JpaRepository를 상속받는다.
    Page<Board> findAll(Pageable pageable);
    List<Board> findByTitleContainingOrContentContaining(String title, String content);
}
