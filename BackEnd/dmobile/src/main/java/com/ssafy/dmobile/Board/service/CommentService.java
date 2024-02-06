package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Comment;
import org.apache.catalina.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

    CommentResponseDTO createComment(CommentRequestDTO dto, Long memberId);

    CommentResponseDTO deleteComment(Long commentId, Long memberId);

    // 갱신
    CommentResponseDTO updateComment(Long commentId, CommentRequestDTO dto, Long memberId);

    // 전체 조회
    List<CommentResponseDTO> getAllComments(Long boardId);

    // 댓글 하나 조회
    CommentResponseDTO getComment(Long id);
}
