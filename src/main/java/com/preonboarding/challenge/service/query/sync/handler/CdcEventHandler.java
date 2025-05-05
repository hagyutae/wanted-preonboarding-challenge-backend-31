package com.preonboarding.challenge.service.query.sync.handler;

import com.preonboarding.challenge.service.query.sync.CdcEvent;

public interface CdcEventHandler {

    boolean canHandle(CdcEvent event);

    void handle(CdcEvent event);
}