package com.ssafy.dmobile.Board.entity;

import com.ssafy.dmobile.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
// Getter 로 getId, getTitle, getContent 자동으로 구현해준다.
@Table(name = "Boards")
@NoArgsConstructor  // 접근 제어자가 protected인 기본 생성자 별도 코드 없이 생성했다.
public class Board {

    // 게시글 ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_Id")
    private Long boardId;

    // title(제목)
    @Column(nullable = false)
    private String title;

    // post_content(내용)
    @Column(nullable = false)
    private String content;

    // User(유저 ID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    private Long createdDate;

    // 태그
    @OneToMany(mappedBy = "board")
    private Set<Tag> tags = new HashSet<>();


    // 댓글
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    // 이미지 기능

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    // 빌더 패턴으로 객체 생성
    @Builder
    public Board(Long id, String title, String content, Member member, Long createdDate) {
        this.boardId = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.createdDate = createdDate;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
