package com.ssafy.dmobile.Board.controller;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.Dto.response.CommentResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.service.BoardService;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/boards")
@RequiredArgsConstructor
@Tag(name = "Board", description = "게시판 관련 API")
public class BoardController {

    private final BoardService boardService;
    private final AuthTokensGenerator authTokensGenerator;

    // 게시판 작성
    @Operation(summary = "게시판 등록", description = "특정 게시판을 등록합니다.")
    @PostMapping
    // requestbody로 요청 받은 본문 값 매핑
    public ResponseEntity<BoardResponseDTO> createBoard(@RequestBody BoardRequestDTO boardRequestDto,
                                                          @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        BoardResponseDTO boardResponseDto = boardService.createBoard(boardRequestDto, memberId);
        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    // 게시판 수정
    @Operation(summary = "게시판 수정", description = "게시판을 수정합니다.")
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> updateBoard(@PathVariable Long boardId,
                                                        @RequestBody BoardRequestDTO boardRequestDto,
                                                        @RequestHeader("Authorization") String token) {
        Long memberId = authTokensGenerator.extractMemberId(token);
        BoardResponseDTO boardResponseDto = boardService.updateBoard(boardId, boardRequestDto, memberId);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

//     게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
        List<BoardResponseDTO> dtoList = boardService.getAllBoards();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    // 게시글 단건 조회
    @Operation(summary = "게시판 조회", description = "게시판을 하나 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> findBoard(@PathVariable Long boardId) {
        BoardResponseDTO dto = boardService.getBoard(boardId);
//        List<CommentResponseDTO> comments = dto.getComments();
//        if (comments != null && comments.isEmpty()) {
//            model.addAttribute("comments", comments);
//        }
//        model.addAttribute("boards", dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "게시판 삭제", description = "게시판을 삭제합니다.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> deleteBoard(@PathVariable Long boardId,
                                                        @RequestHeader("Authorization") String token) {
        // 토큰에서 memberId 추출하는 로직
        Long memberId = authTokensGenerator.extractMemberId(token);

        // 서비스 호출 시 memberId도 함께 전달
        boardService.deleteBoard(boardId, memberId);
//        BoardResponseDTO deletedBoard = boardService.deleteBoard(boardId, memberId);
        return ResponseEntity.noContent().build();
    }

    // GET /boards?page=0&size=10&sort=id,desc
    @Operation(summary = "게시판 페이징 출력", description = "페이징 기능을 추가한 게시판 조회 기능")
    @GetMapping("/paging")
    public ResponseEntity<Page<Board>> findAllBoards(Pageable pageable) {
        Page<Board> boards = boardService.findAllBoards(pageable);
        return ResponseEntity.ok(boards);
    }


}
