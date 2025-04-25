package com.mkhwang.wantedcqrs.config.i18n;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum Lang {
  KO_KR("ko-kr", Locale.KOREAN), EN_US("en-us", Locale.US);

  private final String code;
  private final Locale locale;

  Lang(String code, Locale locale) {
    this.code = code;
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getCode() {
    return code;
  }

  public static Lang from(String code) {
    for (Lang lang : values()) {
      if (lang.code.equalsIgnoreCase(code)) {
        return lang;
      }
    }
    throw new IllegalArgumentException("Unknown language code: " + code);
  }

  @JsonCreator
  public static Lang fromJson(String code) {
    return from(code);
  }

  @Override
  public String toString() {
    return this.getCode();
  }
}

