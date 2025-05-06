package com.sandro.wanted_shop.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class ObjectMapperUtil {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
    public static String writeValueAsString(Object value) {
        return mapper.writeValueAsString(value);
    }

    @SneakyThrows
    public static <T> T readValue(String value, Class<T> clazz) {
        return mapper.readValue(value, clazz);
    }

    @SneakyThrows
    public static <T> T readValue(String value, TypeReference<T> typeReference) {
        return mapper.readValue(value, typeReference);
    }

}
