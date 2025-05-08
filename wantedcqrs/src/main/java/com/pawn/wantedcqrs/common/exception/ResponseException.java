package com.pawn.wantedcqrs.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ResponseException extends RuntimeException {

	@EqualsAndHashCode.Include
	private int status;

	@EqualsAndHashCode.Include
	private String code;

	@EqualsAndHashCode.Include
	private String message;

	@EqualsAndHashCode.Include
	private Map<String, String> details = null;

	public ResponseException(ResponseException responseException) {
		this.status = responseException.status;
		this.code = responseException.code;
		this.message = responseException.message;
		this.details = responseException.details;
	}

	public ResponseException(HttpStatus status, String code, String message) {
		this.status = status.value();
		this.code = code;
		this.message = message;
	}

	@Override
	protected ResponseException clone() {
		return new ResponseException(this);
	}

}
