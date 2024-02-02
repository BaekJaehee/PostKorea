package com.ssafy.dmobile.Board.Dto.request;

import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.member.entity.Member;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDTO {
    private Long commentId;
    private String content;
    private Long createdDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    private Long memberId;
    private Long boardId;

    public Comment dtoToEntity(CommentRequestDTO dto) {
        return Comment.builder()
//                .commentId(dto.getCommentId())
                .content(dto.getContent())
                .createdDate(dto.getCreatedDate())
//                .member(dto.getMember())
//                .board(dto.getBoardId())
                .build();
    }
}
