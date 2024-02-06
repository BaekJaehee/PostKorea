package com.ssafy.dmobile.Board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    // 이미지를 따로 만들어서 엮는게 더 편할 듯..?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "access_url")
    private String accessUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_Id")
    private Board board;

    // lombok 사용하면 아래 코드(생성자) 생략할 수 있는데 그게 더 어려워서 일단 적어놓음.
    public Image(Board board, String fileName, String accessUrl) {
        this.fileName = fileName;
        this.accessUrl = accessUrl;
        this.board = board;
    }
}
