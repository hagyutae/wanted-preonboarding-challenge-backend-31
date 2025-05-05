package com.preonboarding.challenge.service.query.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CdcEvent {
    /**
     * c: create (insert)
     * u: update
     * d: delete
     * r: read (snapshot)
     */
    private String op;

    private Map<String, Object> before;
    private Map<String, Object> after;

    private Source source;

    @JsonProperty("ts_ms")
    private Long timestamp;

    // 이벤트 키를 저장할 별도 필드 추가
    private Map<String, Object> key;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Source {
        private String version;
        private String connector;
        private String name;

        @JsonProperty("ts_ms")
        private Long timestamp;

        private String db;
        private String schema;
        private String table;

        @JsonProperty("txId")
        private Long transactionId;

        @JsonProperty("lsn")
        private Long lsn;
    }

    // 이벤트 유형 확인 헬퍼 메서드
    public boolean isDelete() {
        return "d".equals(op);
    }

    public boolean isCreate() {
        return "c".equals(op);
    }

    public boolean isUpdate() {
        return "u".equals(op);
    }

    public boolean isRead() {
        return "r".equals(op);
    }

    public String getTable() {
        return source != null ? source.getTable() : null;
    }

    public Map<String, Object> getBeforeData() {
        return before;
    }

    public Map<String, Object> getAfterData() {
        return after;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}