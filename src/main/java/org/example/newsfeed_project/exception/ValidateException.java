package org.example.newsfeed_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidateException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ValidateException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
