package com.ssafy.dmobile.board.Dto.response;

import com.ssafy.dmobile.board.entity.Comment;
import lombok.*;


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
