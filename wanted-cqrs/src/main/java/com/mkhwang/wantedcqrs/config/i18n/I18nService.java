package com.mkhwang.wantedcqrs.config.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class I18nService {
  private final MessageSource messageSource;

  private Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

  public String getMessage(String key, Object... args) {
    try {
      return this.messageSource.getMessage(key, args, this.getLocale());
    } catch (NoSuchMessageException e) {
      return key + " not found";
    }
  }
}
