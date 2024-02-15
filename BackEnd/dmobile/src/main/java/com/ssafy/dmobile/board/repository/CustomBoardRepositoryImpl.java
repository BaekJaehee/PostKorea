package com.ssafy.dmobile.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.dmobile.board.entity.Board;
import com.ssafy.dmobile.board.entity.QBoard;
import com.ssafy.dmobile.board.entity.QBoardTag;
import com.ssafy.dmobile.board.entity.QTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Board> findBoardsByTags(List<String> tags) {
        QBoard board = QBoard.board;
        QBoardTag boardTag = QBoardTag.boardTag;
        QTag tag = QTag.tag;

        BooleanExpression condition = tag.tagName.in(tags).and(boardTag.tag.eq(tag)).and(boardTag.board.eq(board));

        return queryFactory.selectDistinct(board)
                .from(board)
                .leftJoin(board.tags, boardTag)
                .where(anyOfTags(tags))
                .fetch();
    }
    private BooleanExpression anyOfTags(List<String> tags) {
        QTag tag = QTag.tag;
        BooleanExpression condition = null;
        for (String tagName : tags) {
            if (condition == null) {
                condition = tag.tagName.eq(tagName);
            } else {
                condition = condition.or(tag.tagName.eq(tagName));
            }
        }
        return condition;
    }

    @Override
    public List<Board> findBoardsByTagsAnd(List<String> tags) {
        QBoard board = QBoard.board;
        QBoardTag boardTag = QBoardTag.boardTag;

        BooleanExpression hasFirstTag = board.tags.any().tag.tagName.eq(tags.get(0));
        BooleanExpression hasSecondTag = board.tags.any().tag.tagName.eq(tags.get(1));

        BooleanExpression combinedCondition = hasFirstTag.and(hasSecondTag);

        log.debug(combinedCondition.toString());

        return queryFactory
                .selectFrom(board)
                .where(combinedCondition)
                .fetch();
    }
}
