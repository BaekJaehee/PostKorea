package com.ssafy.dmobile.Board.Dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.dmobile.Board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Builder
@AllArgsConstructor  // 모든 필드 값을 파라미터로 받는 생성자 추가
@NoArgsConstructor  // 기본 생성자 추가
public class BoardRequestDTO {
//    @JsonProperty("board_id")
    private Long boardId;
    private String title;
    private String content;
//    private LocalDateTime boardcreated;
    private Long createdDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    // 생성자를 사용해 객체 생성 ToEntity : 빌더 패턴을 사용해 DTO를 엔티티로 만들어주는 메서드
    // 블로그에 글을 추가할 때 저장할 엔티티로 변환하는 용도로 사용한다.
    public Board dtoToEntity(BoardRequestDTO dto) {
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdDate(dto.getCreatedDate())
                .build();
    }
}
