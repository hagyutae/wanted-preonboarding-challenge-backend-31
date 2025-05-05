package com.preonboarding.challenge.query.sync.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.sync.CdcEvent;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
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

    protected Double getDoubleValue(Map<String, Object> data, String key) {
        if (data.containsKey(key) && data.get(key) != null) {
            Object value = data.get(key);
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else {
                try {
                    return Double.valueOf(value.toString());
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
}