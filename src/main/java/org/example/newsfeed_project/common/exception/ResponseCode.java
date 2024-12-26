package org.example.newsfeed_project.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ResponseCode {

	PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다. 다시 입력해주세요."),
	URL_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 경로입니다."),
	EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
	USER_ALREADY_DELETE(HttpStatus.BAD_REQUEST, "이미 탈퇴 처리 된 회원입니다."),
	PASSWORD_SAME_AS_BEFORE(HttpStatus.BAD_REQUEST, "바꾸려는 비밀번호가 이전과 동일하거나, 입력한 비밀번호가 서로 다릅니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
	EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 아이디를 찾을 수 없습니다. 다시 확인해주세요."),
	ID_MISMATCH(HttpStatus.UNAUTHORIZED, "권한이 존재하지 않습니다."),
	ORDER_NOT_FOUND(HttpStatus.BAD_REQUEST, "지원하지 않습니다."),
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),
	CANNOT_LIKE_SELF_POST(HttpStatus.BAD_REQUEST, "본인의 게시글에는 좋아요를 누를 수 없습니다."),
	CANNOT_SELF_FOLLOW(HttpStatus.CONFLICT, "본인을 팔로우 할 수 없습니다."),
	USER_ALREADY_FOLLOW(HttpStatus.CONFLICT, "이미 팔로우 한 회원입니다."),
	NOT_FOLLOW_RELATION(HttpStatus.BAD_REQUEST, "팔로잉 되어 있지 않은 회원입니다."),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
	CANNOT_LIKE_SELF_COMMENT(HttpStatus.BAD_REQUEST, "본인의 댓글에는 좋아요를 누를 수 없습니다.");

	private final HttpStatus status;
	private final String message;

	ResponseCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
