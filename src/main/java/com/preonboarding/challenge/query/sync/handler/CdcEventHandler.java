package com.preonboarding.challenge.query.sync.handler;

import com.preonboarding.challenge.query.sync.CdcEvent;

public interface CdcEventHandler {

    boolean canHandle(CdcEvent event);

    void handle(CdcEvent event);
}