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
// Getter 로 getId, getTitle, getContent 자동으로 구현해준다.
@Table(name = "Boards")
@NoArgsConstructor  // 접근 제어자가 protected인 기본 생성자 별도 코드 없이 생성했다.
public class Board {

    // 게시글 ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    // title(제목)
    @Column(nullable = false)
    private String title;

    // post_content(내용)
    @Column(nullable = false)
    private String content;

    // User(유저 ID)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    // post_image(이미지 업로드)

    // post_created(작성시간)
//    @Column(name = "boardcreated")
//    private Long boardcreated;
//    private LocalDateTime boardcreated;

    // 태그
    @OneToMany(mappedBy = "board")
    private Set<Tag> tags = new HashSet<>();


    // 댓글
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    // 빌더 패턴으로 객체 생성
    @Builder
    public Board(Long id, String title, String content, Member member, LocalDateTime boardcreated) {
        this.boardId = id;
        this.title = title;
        this.content = content;
//        this.member = member;
//        this.boardcreated = boardcreated;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
