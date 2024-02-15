package com.ssafy.dmobile.board.Dto.request;

import com.ssafy.dmobile.board.entity.Comment;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDTO {
    private Long commentId;
    private String content;
    private Long createdDate = new Date().getTime();
//    LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    private Long memberId;
    private Long boardId;

    public Comment dtoToEntity(CommentRequestDTO dto) {
        return Comment.builder()
                .content(dto.getContent())
                .createdDate(dto.getCreatedDate())
                .build();
    }
}
