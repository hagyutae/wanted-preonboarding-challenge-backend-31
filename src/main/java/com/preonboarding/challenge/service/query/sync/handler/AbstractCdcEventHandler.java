package com.preonboarding.challenge.service.query.sync.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.service.query.sync.CdcEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Base64;
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

    protected String getStringValue(Map<String, Object> data, String key) {
        return data.containsKey(key) && data.get(key) != null ? data.get(key).toString() : null;
    }

    protected Long getLongValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                log.warn("Cannot parse Long from string value for key '{}': {}", key, value);
                return null;
            }
        }
        return null;
    }

    protected Integer getIntegerValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                log.warn("Cannot parse Integer from string value for key '{}': {}", key, value);
                return null;
            }
        }
        return null;
    }

    protected Double getDoubleValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                log.warn("Cannot parse Double from string value for key '{}': {}", key, value);
                return null;
            }
        }

        return null;
    }

    protected BigDecimal getBigDecimalValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            String stringValue = (String) value;
            int knownScale = 2;
            BigDecimal decoded = decodeDecimalFromBase64(stringValue, knownScale);
            if (decoded != null) {
                return decoded;
            }

            try {
                return new BigDecimal(stringValue);
            } catch (NumberFormatException e) {
                log.warn("Cannot parse BigDecimal from string value for key '{}': {}", key, stringValue);
                return null;
            }
        }
        else if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }
        else {
            log.warn("Unsupported type for BigDecimal conversion for key '{}': {}", key, value.getClass().getName());
            return null;
        }
    }

    private BigDecimal decodeDecimalFromBase64(String base64Value, int scale) {
        if (base64Value == null) {
            return null;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Value);
            // 바이트 배열을 BigInteger (unscaled value)로 변환
            BigInteger unscaledValue = new BigInteger(decodedBytes);
            // BigInteger와 scale을 사용하여 BigDecimal 생성
            return new BigDecimal(unscaledValue, scale);
        } catch (IllegalArgumentException e) {
            log.error("Failed to decode Base64 string '{}' for decimal value: {}", base64Value, e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Error creating BigDecimal from decoded bytes (scale={}): {}", scale, e.getMessage());
            return null;
        }
    }

    protected Boolean getBooleanValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value != null) {
            String stringValue = value.toString().toLowerCase();
            return "true".equals(stringValue) || "1".equals(stringValue) || "yes".equals(stringValue) || "t".equals(stringValue) || "y".equals(stringValue);
        }
        return null;
    }
}