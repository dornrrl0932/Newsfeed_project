package org.example.newsfeed_project.common.exception;

//RuntimeException을 상속받아 커스텀 예외 생성
public class UserDeletedException extends RuntimeException{

    public UserDeletedException (String message){
        super(message);
    }
}
