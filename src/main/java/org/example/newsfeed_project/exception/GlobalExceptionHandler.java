package org.example.newsfeed_project.exception;

import org.example.newsfeed_project.follow.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 컨트롤러 전역에 활성화
// 예외 관리
public class GlobalExceptionHandler {

    // ValidateException 처리
    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<MessageDto> handleValidateException(ValidateException exception) {
        return new ResponseEntity<>(new MessageDto(exception.getMessage()), exception.getHttpStatus());
    }
}
