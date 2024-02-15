package com.ssafy.dmobile.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardTag {

    @EmbeddedId
    private BoardTagKey boardTagKeyId;

    @ManyToOne
    @MapsId("boardId")
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
