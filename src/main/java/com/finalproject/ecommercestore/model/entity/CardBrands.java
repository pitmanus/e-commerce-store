package com.finalproject.ecommercestore.model.entity;

public enum CardBrands {
    VISA("Visa"),
    MASTER_CARD("Master card");

    private final String displayValue;

    CardBrands(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}
