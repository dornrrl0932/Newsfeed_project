package org.example.newsfeed_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
	private int status; // 응답코드
	private String message; // 응답 메시지
	private T data; // 실제 응답 데이터

	public static <T> ApiResponse<T> success(int status, String message, T data) {
		return ApiResponse.<T>builder()
			.status(status)
			.message(message)
			.data(data)
			.build();
	}

	public static <T> ApiResponse<T> error(int status, String errorMessage) {
		return ApiResponse.<T>builder()
			.status(status)
			.message(errorMessage)
			.build();
	}
}
