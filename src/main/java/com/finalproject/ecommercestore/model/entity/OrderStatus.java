package com.finalproject.ecommercestore.model.entity;

public enum OrderStatus {
    PENDING("Pending"),
    AWAITING_PAYMENT("Awaiting payment"),
    AWAITING_FULFILLMENT("Awaiting fulfilment"),
    AWAITING_SHIPMENT("Awaiting shipment"),
    AWAITING_PICKUP("Awaiting pickup"),
    PARTIALLY_SHIPPED("Partially shipped"),
    SHIPPED("Shipped"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    DECLINED("Declined"),
    REFUNDED("Refunded"),
    DISPUTED("Disputed"),
    PARTIALLY_REFUNDED("Partially refunded");

    private final String displayValue;

    OrderStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }

}
