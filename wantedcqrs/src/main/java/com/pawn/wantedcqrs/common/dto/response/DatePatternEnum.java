package com.pawn.wantedcqrs.common.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public enum DatePatternEnum {
	DATETIME_DEFAULT("yyyy-MM-dd HH:mm:ss");

	public String value;

	DatePatternEnum(String value) {
		this.value = value;
	}

	public DateTimeFormatter formatter() {
		return DateTimeFormatter.ofPattern(this.value).withLocale(Locale.forLanguageTag("ko"));
	}

}
