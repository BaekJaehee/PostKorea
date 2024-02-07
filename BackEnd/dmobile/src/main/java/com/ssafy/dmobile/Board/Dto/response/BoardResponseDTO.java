package com.ssafy.dmobile.Board.Dto.response;

import com.ssafy.dmobile.Board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDTO {
    private Long boardId;
    private String title;
    private String content;
    private Long memberId;  // 작성자
//    private Long boardcreated = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    private Long createdate;
    private List<CommentResponseDTO> comments;
    private List<ImageResponseDTO> imageUrls;


    public BoardResponseDTO(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdate = board.getCreatedDate();
        this.memberId = board.getMember().getId();
        this.comments = board.getComments().stream().map(CommentResponseDTO::new).collect(Collectors.toList());
        this.imageUrls = board.getImages().stream()
                .map(image -> new ImageResponseDTO(image.getImageId(), image.getFileName(), image.getAccessUrl()))
                .collect(Collectors.toList());
    }
}
