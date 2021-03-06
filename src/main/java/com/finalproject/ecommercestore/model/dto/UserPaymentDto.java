package com.finalproject.ecommercestore.model.dto;

import com.finalproject.ecommercestore.model.entity.Address;
import com.finalproject.ecommercestore.model.entity.CardBrands;
import com.finalproject.ecommercestore.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public class UserPaymentDto {

    private Long id;
    private CardBrands cardBrand;
    private String cardName;
    private String bankName;
    private String cardNumber;
    private int expiryMonth;
    private int expiryYear;
    private int cvc;
    private String holderName;
    private boolean defaultPayment;
    private MultipartFile cardImage;

    private AddressDto billingAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardBrands getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(CardBrands cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public boolean isDefaultPayment() {
        return defaultPayment;
    }

    public void setDefaultPayment(boolean defaultPayment) {
        this.defaultPayment = defaultPayment;
    }

    public AddressDto getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDto billingAddress) {
        this.billingAddress = billingAddress;
    }

    public MultipartFile getCardImage() {
        return cardImage;
    }

    public void setCardImage(MultipartFile cardImage) {
        this.cardImage = cardImage;
    }
}
