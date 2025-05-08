package com.pawn.wantedcqrs.product.entity;

public enum ProductStatus {

    ACTIVE, SOLD_OUT, DELETED;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isSoldOut() {
        return this == SOLD_OUT;
    }

    public boolean isDeleted() {
        return this == DELETED;
    }

}
