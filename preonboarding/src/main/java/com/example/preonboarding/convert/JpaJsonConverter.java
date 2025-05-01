package com.example.preonboarding.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.util.Map;
@Converter
public class JpaJsonConverter implements AttributeConverter<JsonNode, PGobject> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public PGobject convertToDatabaseColumn(JsonNode attribute) {
        try {
            PGobject pGobject = new PGobject();
            pGobject.setType("jsonb");
            pGobject.setValue(objectMapper.writeValueAsString(attribute));
            return pGobject;
        } catch (Exception e) {
            throw new RuntimeException("jsonb 직렬화 실패", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(PGobject dbData) {
        try {
            return objectMapper.readTree(dbData.getValue());
        } catch (Exception e) {
            throw new RuntimeException("jsonb 역직렬화 실패", e);
        }
    }
}
