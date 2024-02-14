package com.ssafy.dmobile.Board.repository;

import com.ssafy.dmobile.Board.entity.Board;

import java.util.List;

public interface CustomBoardRepository {
    List<Board> findBoardsByTags(List<String> tags);
    List<Board> findBoardsByTagsAnd(List<String> tags);
}
