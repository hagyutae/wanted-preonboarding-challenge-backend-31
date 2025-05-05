package com.preonboarding.challenge.query.sync.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractCdcEventHandler implements CdcEventHandler {

    protected final ObjectMapper objectMapper;

    @Override
    public boolean canHandle(CdcEvent event) {
        return getSupportedTable().equals(event.getTable());
    }

    // 핸들러가 지원하는 테이블 이름 반환
    protected abstract String getSupportedTable();

    // 공통 유틸리티 메서드들
    protected String getStringValue(Map<String, Object> data, String key) {
        return data.containsKey(key) && data.get(key) != null ? data.get(key).toString() : null;
    }

    protected Long getLongValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).longValue();
            } else {
                try {
                    return Long.valueOf(value.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    protected Integer getIntegerValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else {
                try {
                    return Integer.valueOf(value.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    protected BigDecimal getBigDecimalValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return new BigDecimal(value.toString());
            } else {
                try {
                    return new BigDecimal(value.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    protected Boolean getBooleanValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Boolean) {
                return (Boolean) value;
            } else {
                return "true".equalsIgnoreCase(value.toString()) ||
                        "1".equals(value.toString()) ||
                        "yes".equalsIgnoreCase(value.toString());
            }
        }
        return null;
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