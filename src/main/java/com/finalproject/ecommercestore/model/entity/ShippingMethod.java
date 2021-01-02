package com.finalproject.ecommercestore.model.entity;

public enum ShippingMethod {
    STANDART_SHIPPING("Standart shipping (3-5 working days)"),
    FEDEX("Fedex (3-4 working days)"),
    DHL("DHL (2-3 working days)"),
    UPS("UPS (1-2 working days)");

    private final String displayValue;

    ShippingMethod(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}
