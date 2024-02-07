package com.ssafy.dmobile.Board.entity;

import com.ssafy.dmobile.member.entity.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    // content
    @Column(name = "comment_content", nullable = false)
    private String content;

    // User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // board_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    // createdTime
    @Column(name = "comment_created")
    private Long createdDate;

//    @Getter
//    private Board board;

    @Builder
    public Comment(Long commentId, Board board, String content, Member member, Long createdDate) {
        this.commentId = commentId;
        this.board = board;
        this.content = content;
        this.member = member;
        this.createdDate = createdDate;
    }

    public void update(String content) {
        this.content = content;
    }

}
