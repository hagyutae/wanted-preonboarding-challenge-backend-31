package com.wanted.mono.global.util;

import com.wanted.mono.global.message.MessageCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    public String get(MessageCode messageCode, Object... args) {
        return messageSource.getMessage(messageCode.getCode(), args, LocaleContextHolder.getLocale());
    }
}

