package com.preonboarding.challenge.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.sync.handler.AbstractCdcEventHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public abstract class ProductSearchModelEventHandler extends AbstractCdcEventHandler {
    public ProductSearchModelEventHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    protected Instant parseTimestampToInstant(Object timestamp) {
        if (timestamp == null) {
            return null;
        }

        if (timestamp instanceof String) {
            try {
                return LocalDateTime.parse((String) timestamp).atZone(ZoneId.systemDefault()).toInstant();
            } catch (Exception e) {
                log.warn("Failed to parse timestamp string: {}", timestamp);
                return null;
            }
        } else if (timestamp instanceof Number) {
            try {
                // 마이크로초 단위를 밀리초로 변환 (1/1000)
                long microseconds = ((Number) timestamp).longValue();
                long milliseconds = microseconds / 1000;

                return Instant.ofEpochMilli(milliseconds);
            } catch (Exception e) {
                log.warn("Failed to parse timestamp number: {}", timestamp);
                return null;
            }
        }

        return null;
    }
}
