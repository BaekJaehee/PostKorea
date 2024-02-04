package com.ssafy.dmobile.Board.service;

import com.ssafy.dmobile.Board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.Board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.repository.BoardRepository;
import com.ssafy.dmobile.exception.CustomException;
import com.ssafy.dmobile.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.utils.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service  // 빈으로 등록
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor  // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public BoardResponseDTO createBoard(BoardRequestDTO dto, Long memberId) {
        if (dto.getTitle().trim().isEmpty()) {
            throw new CustomException(ExceptionType.TITLE_CANNOT_BE_EMPTY);
        }
        if (dto.getContent().trim().isEmpty()) {
            throw new CustomException(ExceptionType.CONTENT_CANNOT_BE_EMPTY);
        }
        // 작성자가 없으면 못 만들게 해야 함 + 현재 토큰을 넘겨줘야 함
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ExceptionType.MEMBER_NOT_FOUND_EXCEPTION)
        );
        Board board = dto.dtoToEntity(dto);
        board.setMember(member);
        Board save = boardRepository.save(board);
        return new BoardResponseDTO(save);
    }

    @Override
    @Transactional
    public BoardResponseDTO updateBoard(Long id, BoardRequestDTO dto, Long memberId) {
        // 게시판이 있는지
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionType.BOARD_NOT_FOUND)
        );
        // 수정한 게시판 제목이 있는지
        if (dto.getTitle().trim().isEmpty()) {
            throw new CustomException(ExceptionType.TITLE_CANNOT_BE_EMPTY);
        }
        // 수정한 게시판 내용이 있는지
        if (dto.getContent().trim().isEmpty()) {
            throw new CustomException(ExceptionType.CONTENT_CANNOT_BE_EMPTY);
        }
        // 게시판 작성자와 현재 로그인된 사람의 아이디가 같은지 비교
        if (!board.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionType.USER_NOT_AUTHORIZED_TO_UPDATE_THIS_BOARD);
        }
        board.update(dto.getTitle(), dto.getContent());
        Board save = boardRepository.save(board);
        return new BoardResponseDTO(save);
    }

    @Override
    // 전체 조회 repository.findAll();
    public List<BoardResponseDTO> getAllBoards() {
        List<Board> list = boardRepository.findAll();
        List<BoardResponseDTO> dtoList = new ArrayList<>();
        list.stream().forEach(findAll -> dtoList.add(new BoardResponseDTO(findAll)));
        return dtoList;
    }

    @Override
    public BoardResponseDTO getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionType.BOARD_NOT_FOUND)
        );
        return new BoardResponseDTO(board);
    }

    @Override
    @Transactional
    public BoardResponseDTO deleteBoard(Long id, Long memberId) {
        // 게시판이 있는지 확인
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionType.BOARD_NOT_FOUND)
        );

        // 게시판 작성자와 현재 로그인된 사용자의 ID 비교
        if (!board.getMember().getId().equals(memberId)) {
            throw new CustomException(ExceptionType.USER_NOT_AUTHORIZED_TO_UPDATE_THIS_BOARD);
        }
        boardRepository.deleteById(id);
        return new BoardResponseDTO(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Board> findAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
}
