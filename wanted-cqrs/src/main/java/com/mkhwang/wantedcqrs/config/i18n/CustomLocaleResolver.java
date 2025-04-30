package com.mkhwang.wantedcqrs.config.i18n;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

  private static final Locale DEFAULT_LOCALE = new Locale("ko", "KR");

  // 매핑 테이블: 언어 코드만 왔을 때 보정할 국가 정보
  private static final Map<String, Locale> LANGUAGE_TO_LOCALE =
      Map.of("ko", new Locale("ko", "KR"), "en", new Locale("en", "US"));

  @Override
  @NonNull
  public Locale resolveLocale(@NonNull HttpServletRequest request) {
    String headerLang = request.getHeader("Accept-Language");

    if (headerLang == null || headerLang.isBlank()) {
      return DEFAULT_LOCALE;
    }

    // 파싱: 가장 우선순위가 높은 언어 코드만 추출
    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(headerLang);
    for (Locale.LanguageRange range : languageRanges) {
      String lang = range.getRange();

      Locale locale = Locale.forLanguageTag(lang);

      if (locale != null && !this.getSupportedLocales().isEmpty()
          && this.getSupportedLocales().contains(locale)) {
        return locale;
      }

      if (LANGUAGE_TO_LOCALE.containsKey(lang)) {
        return LANGUAGE_TO_LOCALE.get(lang);
      }
    }

    // fallback
    return DEFAULT_LOCALE;
  }
}
