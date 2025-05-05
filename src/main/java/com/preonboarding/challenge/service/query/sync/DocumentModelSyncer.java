package com.preonboarding.challenge.service.query.sync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.service.query.sync.handler.CdcEventHandler;
import com.preonboarding.challenge.service.query.sync.handler.document.ProductDocumentModelEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentModelSyncer {

    private final ObjectMapper objectMapper;
    private final List<ProductDocumentModelEventHandler> documentEventHandlers;

    @KafkaListener(topics = {"product-events", "product-option-events", "seller-events", "brand-events", "tag-events", "category-events"},
            groupId = "document-sync-group")
    public void consumeDocumentEvents(
            @Payload String messageValue,
            @Header(KafkaHeaders.RECEIVED_KEY) String messageKey,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        try {
            log.debug("Received document CDC event - Topic: {}, Key: {}, Value: {}", topic, messageKey, messageValue);

            // 값 데이터 파싱
            CdcEvent event = objectMapper.readValue(messageValue, CdcEvent.class);

            // 키 데이터 파싱 및 설정
            if (messageKey != null && !messageKey.isEmpty()) {
                Map<String, Object> keyData = objectMapper.readValue(messageKey,
                        new TypeReference<Map<String, Object>>() {});
                event.setKey(keyData);
            }

            String table = event.getTable();
            if (table == null) {
                log.warn("Table name missing in event");
                return;
            }

            // 적절한 핸들러를 찾아 이벤트 처리 위임
            boolean handled = false;
            for (CdcEventHandler handler : documentEventHandlers) {
                if (handler.canHandle(event)) {
                    handler.handle(event);
                    handled = true;
                    break;
                }
            }

            if (!handled) {
                log.debug("No document handler found for table: {}", table);
            }

        } catch (JsonProcessingException e) {
            log.error("Error parsing document CDC event: {}", messageValue, e);
        } catch (Exception e) {
            log.error("Error processing document CDC event: {}", messageValue, e);
        }
    }
}