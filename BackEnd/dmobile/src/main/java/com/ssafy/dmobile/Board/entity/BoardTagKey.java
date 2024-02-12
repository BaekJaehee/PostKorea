package com.ssafy.dmobile.Board.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardTagKey implements Serializable {
    private Long boardId;
    private Long tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardTagKey)) return false;
        BoardTagKey that = (BoardTagKey) o;
        return Objects.equals(boardId, that.boardId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, tagId);
    }
}
