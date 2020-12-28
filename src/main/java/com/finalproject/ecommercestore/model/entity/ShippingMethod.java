package com.finalproject.ecommercestore.model.entity;

public enum ShippingMethod {
    STANDART_SHIPPNG("Standart shipping"),
    EMS("EMS"),
    FEDEX("Fedex"),
    DHL("DHL"),
    UPS_EXPRESS_SAVER("UPS Express Saver");

    private final String displayValue;

    ShippingMethod(String displayValue) {
        this.displayValue = displayValue;
    }
}
