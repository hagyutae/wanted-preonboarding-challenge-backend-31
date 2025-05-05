package com.preonboarding.challenge.query.sync.handler.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preonboarding.challenge.query.sync.handler.AbstractCdcEventHandler;

public abstract class ProductSearchCdcEventHandler extends AbstractCdcEventHandler {
    public ProductSearchCdcEventHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
