package org.example.newsfeed_project.commen.exception;

//RuntimeException을 상속받아 커스텀 예외 생성
public class InvalidUrlException extends RuntimeException{

    public InvalidUrlException (String massege) {
        super(massege);
    }
}
