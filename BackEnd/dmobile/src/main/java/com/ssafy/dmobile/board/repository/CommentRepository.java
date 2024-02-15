package com.ssafy.dmobile.board.repository;

import com.ssafy.dmobile.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard_boardId(Long boardId);
}
