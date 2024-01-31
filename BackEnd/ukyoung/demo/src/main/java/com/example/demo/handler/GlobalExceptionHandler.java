package com.example.demo.handler;

import com.example.demo.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(int code, String message, HttpStatus status) {

        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("msg", message);

        return ResponseEntity.status(status).body(response);
    }

    // 핸들링 가능한 오류에 대한 Exception
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.error("handleCustomException throw Exception : {}", e.getExceptionType());
        return buildResponseEntity(100, e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException e) {
        log.error("handleCustomException throw Exception : {}", e.getMessage());
        return buildResponseEntity(100, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        // 로그에 예외 정보 기록
        log.error("unhandledException throw Exception : {}", e.getMessage());
        return buildResponseEntity(500, "예상치 못한 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
