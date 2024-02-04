package com.ssafy.dmobile.Board.controller;

import com.ssafy.dmobile.Board.Dto.request.CommentRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.Board.service.BoardService;
import com.ssafy.dmobile.Board.service.CommentService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
@Tag(name = "comment", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final AuthTokensGenerator authTokensGenerator;

    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long boardId,
                                                            @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        commentRequestDTO.setBoardId(boardId);
        CommentResponseDTO commentResponseDTO = commentService.createComment(commentRequestDTO, memberId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PutMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        CommentResponseDTO commentResponseDTO = commentService.updateComment(commentId, commentRequestDTO, memberId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }


    // 개별 삭제
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable long commentId,
                                                            @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.noContent().build();
    }

    // 단건 조회
    @Operation(summary = "댓글 단건 조회", description = "댓긇 아이디로 하나 조회")
    @GetMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> getComment(@PathVariable Long commentId) {
        CommentResponseDTO comment = commentService.getComment(commentId);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 이거 제대로 동작 안함.
    @Operation(summary = "댓글 전체 조회", description = "특정 게시판의 댓글 전체 조회")
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getAllComments(@PathVariable Long boardId) {
        List<CommentResponseDTO> comments = commentService.getAllComments(boardId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
