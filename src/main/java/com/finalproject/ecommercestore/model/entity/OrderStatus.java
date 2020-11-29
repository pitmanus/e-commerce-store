package com.finalproject.ecommercestore.model.entity;

public enum OrderStatus {
    PENDING,
    AWAITING_PAYMENT,
    AWAITING_FULFILLMENT,
    AWAITING_SHIPMENT,
    AWAITING_PICKUP,
    PARTIALLY_SHIPPED,
    SHIPPED,
    COMPLETED,
    CANCELLED,
    DECLINED,
    REFUNDED,
    DISPUTED,
    PARTIALLY_REFUNDED
}
