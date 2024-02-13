package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.Board.repository.BoardRepository;
import com.ssafy.dmobile.Board.repository.CommentRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public CommentResponseDTO createComment(CommentRequestDTO dto, Long memberId) {
        // 게시판 존재 여부 확인
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(
                () -> new CustomException(ExceptionType.BOARD_NOT_FOUND));

        // 로그인된 사용자 존재 여부 확인
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION));

        if (dto.getContent().trim().isEmpty()) {
            throw new CustomException(ExceptionType.TITLE_CANNOT_BE_EMPTY);
        }


        // Comment 객체 생성 및 저장
        Comment comment = dto.dtoToEntity(dto);
        comment.setBoard(board);  // 게시물 설정
        comment.setMember(member);  // 작성자 설정
        comment.setCreatedDate(new Date().getTime());
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDTO(saveComment);
    }

    @Override
    @Transactional
    public CommentResponseDTO deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ExceptionType.COMMENT_NOT_FOUND));
        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionType.USER_NOT_AUTHORIZED_TO_UPDATE_THIS_COMMENT);
        }

        commentRepository.deleteById(commentId);
        return new CommentResponseDTO(comment);
    }


    @Override
    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO dto, Long memberId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ExceptionType.COMMENT_NOT_FOUND));
        if (!comment.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionType.USER_NOT_AUTHORIZED_TO_UPDATE_THIS_COMMENT);
        }
        if (dto.getContent().trim().isEmpty()) {
            throw new CustomException(ExceptionType.CONTENT_CANNOT_BE_EMPTY);
        }
        comment.update(dto.getContent());
        Comment save = commentRepository.save(comment);
        return new CommentResponseDTO(save);
    }

    @Override
    @Transactional
    public CommentResponseDTO getComment(Long boardId) {
        Optional<Comment> optionalComment = commentRepository.findById(boardId);
        return optionalComment.map(CommentResponseDTO::new).orElse(null);
    }

    @Override
    @Transactional
    public List<CommentResponseDTO> getAllComments(Long boardId) {
        List<Comment> comments = commentRepository.findByBoard_boardId(boardId);
        List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponseDTOS.add(new CommentResponseDTO(comment));
        }
        return commentResponseDTOS;
    }

}
