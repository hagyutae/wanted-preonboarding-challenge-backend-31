package com.pawn.wantedcqrs.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawn.wantedcqrs.common.exception.ResponseException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommonApiResponse<T> {

	private final boolean success;

	private final T data;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private final LocalDateTime serverDatetime;

	protected CommonApiResponse(boolean success, T data) {
		this.success = success;
		this.data = data;
		this.serverDatetime = LocalDateTime.now();
	}

	public static <T> CommonApiResponse<T> ok(T response) {
		return new CommonApiResponse<>(ApiResult.SUCCESS.value(), response);
	}

	public static CommonApiResponse<ResponseException> error(ResponseException exception) {
		return new CommonApiResponse<>(ApiResult.FAIL.value(), exception);
	}

}