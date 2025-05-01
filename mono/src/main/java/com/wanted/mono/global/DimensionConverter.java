package com.wanted.mono.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.mono.domain.product.dto.Dimension;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter(autoApply = false)
@RequiredArgsConstructor
public class DimensionConverter implements AttributeConverter<Dimension, String> {

    private final ObjectMapper objectMapper;
    @Override
    public String convertToDatabaseColumn(Dimension attribute) {
        if (attribute == null) return null;
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Dimension JSON 직렬화 실패", e);
        }
    }

    @Override
    public Dimension convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return null;
        try {
            return objectMapper.readValue(dbData, Dimension.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Dimension JSON 역직렬화 실패", e);
        }
    }
}

