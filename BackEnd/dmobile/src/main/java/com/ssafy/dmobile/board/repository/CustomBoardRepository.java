package com.ssafy.dmobile.board.repository;

import com.ssafy.dmobile.board.entity.Board;

import java.util.List;

public interface CustomBoardRepository {
    List<Board> findBoardsByTags(List<String> tags);
    List<Board> findBoardsByTagsAnd(List<String> tags);
}
