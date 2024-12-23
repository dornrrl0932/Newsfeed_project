package org.example.newsfeed_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //탈퇴 처리 된 회원 예외처리
    @ExceptionHandler(UserDeletedException.class)
    public ResponseEntity<String> handleUserDeletedException(UserDeletedException e) {

        //400 BAD_REQUEST와 메세지를 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    //잘못 된 URL에 대한 예외처리
    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<String> handleInvalidUrlException(InvalidUrlException e) {

        //404 Not_Found와 메세지를 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("잘못 된 URL입니다. 다시 입력해주세요.");
    }
}

