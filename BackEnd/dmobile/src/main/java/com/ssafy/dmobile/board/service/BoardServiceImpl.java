package com.ssafy.dmobile.board.service;

import com.ssafy.dmobile.board.Dto.request.BoardRequestDTO;
import com.ssafy.dmobile.board.Dto.response.BoardResponseDTO;
import com.ssafy.dmobile.board.entity.*;
import com.ssafy.dmobile.board.repository.*;
import com.ssafy.dmobile.common.exception.CustomException;
import com.ssafy.dmobile.common.exception.ExceptionType;
import com.ssafy.dmobile.member.entity.Member;
import com.ssafy.dmobile.member.repository.MemberRepository;
import com.ssafy.dmobile.common.utils.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service  // 빈으로 등록
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor  // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;
    private final CustomBoardRepository customBoardRepository;

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

        // 태그 처리 로직
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            for (String tagName : dto.getTags()) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                BoardTag boardTag = new BoardTag();

                // BoardTagKey 객체 생성 및 초기화
                BoardTagKey boardTagKey = new BoardTagKey(save.getBoardId(), tag.getTagId());

                // BoardTag 객체에 BoardTagKey 설정
                boardTag.setBoardTagKeyId(boardTagKey);

                // BoardTag 객체에 Board와 Tag 엔티티 참조 설정
                boardTag.setBoard(save); // 여기서 save는 위에서 저장된 Board 엔티티의 인스턴스입니다.
                boardTag.setTag(tag);

                // 설정된 BoardTag 객체 저장
                boardTagRepository.save(boardTag);
            }
        }
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

        // 기존 연결된 태그 처리 (예시에서는 모든 기존 태그를 삭제하고 새 태그를 추가합니다)
        boardTagRepository.deleteByBoardId(board.getBoardId()); // 이 메소드는 직접 구현해야 합니다.

        // 새 태그 처리 로직
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            for (String tagName : dto.getTags()) {
                Tag tag = tagRepository.findByTagName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                BoardTag boardTag = new BoardTag();

                // BoardTagKey 객체 생성 및 초기화
                BoardTagKey boardTagKey = new BoardTagKey(board.getBoardId(), tag.getTagId());

                // BoardTag 객체에 BoardTagKey 설정
                boardTag.setBoardTagKeyId(boardTagKey);

                // BoardTag 객체에 Board와 Tag 엔티티 참조 설정
                boardTag.setBoard(board);
                boardTag.setTag(tag);

                // 설정된 BoardTag 객체 저장
                boardTagRepository.save(boardTag);
            }
        }
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

    // 이미지 기능
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.max-file-size}")
    private String maxFileSize;

//    @Override
//    @Transactional
//    public String uploadBoardImage(Long boardId, MultipartFile file, Long imageId) throws IOException {
//        // 게시판이 없는 경우
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new CustomException(ExceptionType.BOARD_NOT_FOUND));
//        // 용량 제한에 걸리는 경우
//        long parsedMaxFileSize = DataSize.parse(maxFileSize).toBytes();
//        if (file.getSize() > parsedMaxFileSize) {
//            throw new CustomException(ExceptionType.MAX_FILE_SIZE_EXCEPTION);
//        }
//
//        String fileName = boardId + "_" + imageId + ".PNG";
//        Path filePath = Paths.get(uploadDir, fileName);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        // 이미지 엔티티 생성 및 저장
//        Image image = new Image(board, fileName, filePath.toString());
//        imageRepository.save(image);
//
//        // 게시판 엔티티에 이미지 추가 (선택적)
//        board.getImages().add(image);
//        boardRepository.save(board);
//
//        return fileName;
//    }
//
//    // 게시판 수정
//    @Override
//    @Transactional
//    public void updateBoardImage(Long boardId, Long imageId, MultipartFile file) throws IOException {
//        Image image = imageRepository.findById(imageId)
//                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
//
//        String originalFileName = file.getOriginalFilename();
//        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String fileName = boardId + "_" + imageId + fileExtension;
//        Path filePath = Paths.get(uploadDir, fileName);
//
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        image.setFileName(fileName);
//        image.setAccessUrl(filePath.toString());
//        imageRepository.save(image);
//    }
//
//    // 게시판 삭제
//    @Override
//    @Transactional
//    public void deleteBoardImage(Long boardId, Long imageId) {
//        Image image = imageRepository.findById(imageId)
//                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
//
//        Path filePath = Paths.get(image.getAccessUrl());
//        try {
//            Files.deleteIfExists(filePath);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to delete image file on disk", e);
//        }
//
//        imageRepository.delete(image);
//    }

    @Override
    @Transactional
    public Board createBoardWithImages(Long boardId, List<MultipartFile> images) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ExceptionType.BOARD_NOT_FOUND));
        long parsedMaxFileSize = DataSize.parse(maxFileSize).toBytes();
        for (MultipartFile imageFile : images) {
            // 이미지 파일이 5mb를 넘어가는 경우
            if(imageFile.getSize() > parsedMaxFileSize) {
                throw new CustomException(ExceptionType.MAX_FILE_SIZE_EXCEPTION);
            }
            String fileName = imageFile.getOriginalFilename();
            String filePath = s3Service.uploadFile(imageFile); // S3에 이미지 업로드하고 파일 경로 받기

            Image image = new Image(board, fileName, filePath); // Image 엔티티 생성
            board.getImages().add(image); // Board 엔티티에 Image 추가
        }

        boardRepository.save(board);
        return board;
    }
    @Override
    public List<BoardResponseDTO> findBoardsByTag(List<String> tags) {
        List<Board> boards = customBoardRepository.findBoardsByTags(tags);
        return boards.stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDTO> searchBoards(String keyword) {
        List<Board> boards = boardRepository.findByTitleContainingOrContentContaining(keyword, keyword);
        return boards.stream().map(BoardResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDTO> findBoardsByTagsAnd(List<String> tags) {
        // CustomBoardRepository에서 정의한 메소드를 사용하여 게시글 검색
        List<Board> boards = customBoardRepository.findBoardsByTagsAnd(tags);

        // 검색된 게시글을 BoardResponseDTO 리스트로 변환
        return boards.stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }
}
