package com.mkhwang.wantedcqrs.config.i18n;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToLangConverter implements Converter<String, Lang> {

  @Override
  public Lang convert(@NonNull String source) {
    return Lang.from(source);
  }
}

