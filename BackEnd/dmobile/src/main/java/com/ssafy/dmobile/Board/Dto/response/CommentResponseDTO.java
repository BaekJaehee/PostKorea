package com.ssafy.dmobile.Board.Dto.response;

import com.ssafy.dmobile.Board.entity.Board;
import com.ssafy.dmobile.Board.entity.Comment;
import com.ssafy.dmobile.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
public class CommentResponseDTO {
    private Long commentId;
    private String content;
    private Long createdDate;
    private Long memberId;
    private String memberName;
    private String memberAchieve;
    private String memberProfileUrl;
    private Long boardId;

    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.memberId = comment.getMember().getId();
        this.memberName = comment.getMember().getName();
        this.memberAchieve = comment.getMember().getAchieve();
        this.memberProfileUrl = comment.getMember().getProfileUrl();
        this.boardId = comment.getBoard().getBoardId();
    }

}
