package com.ssafy.dmobile.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {

    // 글로벌
    MISSING_HEADER_EXCEPTION(404, "필수 해더가 누락되었습니다."),

    // 회원관리
    DUPLICATE_EMAIL_EXCEPTION(409, "이미 사용중인 이메일입니다."),
    DUPLICATE_NICKNAME_EXCEPTION(409, "이미 사용중인 닉네임입니다."),
    INVALID_LOGIN_EXCEPTION(401, "아이디 또는 비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_FOUND_EXCEPTION(404, "사용자 정보를 찾을 수 없습니다."),
    INVALID_CURRENT_PASSWORD_EXCEPTION(401, "사용자 비밀번호가 일치하지 않습니다."),
    INVALID_PASSWORD_FORMAT_EXCEPTION(401, "비밀번호 형식이 일치하지 않습니다."),
    
    // 파일관리
    MAX_FILE_SIZE_EXCEPTION(401, "저장가능한 파일 용량을 초과하였습니다."),
    FILE_NOT_FOUND_EXCEPTION(404, "해당하는 파일을 찾을 수 없습니다."),

    // 게시판 관리
    TITLE_CANNOT_BE_EMPTY(400, "제목을 입력하세요."),
    CONTENT_CANNOT_BE_EMPTY(400, "내용을 입력하세요."),
    BOARD_NOT_FOUND(404, "게시판을 찾을 수 없습니다."),
    USER_NOT_AUTHORIZED_TO_UPDATE_THIS_BOARD(403, "게시판을 수정할 권한이 없습니다."),
    USER_NOT_AUTHORIZED_TO_UPDATE_THIS_COMMENT(403, "댓글을 수정할 권한이 없습니다."),
    COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다."),
//    FILE_NOT_FOUND_EXCEPTION(404, "해당하는 파일을 찾을 수 없습니다."),

    // 계획관리
    PLAN_NOT_FOUND_EXCEPTION(404, "해당하는 계획을 찾을 수 없습니다."),
    INVALID_MEMBER_FOR_PLAN_EXCEPTION(401, "해당 계획에 접근할 수 있는 사용자가 아닙니다."),
    
    // 문화재 관리
    RELIC_NOT_FOUND_EXCEPTION(404, "해당하는 문화재를 찾을 수 없습니다."),

    // 테마 관리
    THEME_NOT_FOUND_EXCEPTION(404, "해당하는 테마를 찾을 수 없습니다."),

    // 업적 관리
    ACHIEVE_NOT_FOUND_EXCEPTION(404, "해당하는 업적을 찾을 수 없습니다."),
    MAPPING_INFO_NOT_FOUND_EXCEPTION(404, "해당하는 매핑 정보를 찾을 수 없습니다."),
    INVALID_MEMBER_FOR_ACHIEVE_EXCEPTION(401, "해당 업적에 접근할 수 있는 사용자가 아닙니다."),

    // 상점 관리
    PRODUCT_NOT_FOUND_EXCEPTION(404, "해당하는 상품을 찾을 수 없습니다."),
    PRODUCT_DUPLICATE_EXCEPTION(404, "상품은 중복해서 구매할 수 없습니다."),
    PRODUCT_POINT_EXCEPTION(404, "포인트가 부족합니다");

    // 사용자 정의 enum 을 통해 exception 세분화
    private final int code;
    private final String message;

    ExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
