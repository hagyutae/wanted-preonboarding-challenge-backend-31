package com.pawn.wantedcqrs.common.dto.response;

public enum ApiResult {
    SUCCESS {
        @Override
        public boolean value() {
            return true;
        }
    }, FAIL {
        @Override
        public boolean value() {
            return false;
        }
    };

    public abstract boolean value();
}
