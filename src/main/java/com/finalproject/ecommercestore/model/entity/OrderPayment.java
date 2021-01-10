package com.finalproject.ecommercestore.model.entity;

import com.finalproject.ecommercestore.model.dto.AddressDto;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "order_payment")
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_brand")
    @Enumerated(EnumType.STRING)
    private CardBrands cardBrand;
    @Column(name = "card_name")
    private String cardName;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiry_month")
    private int expiryMonth;
    @Column(name = "expiry_year")
    private int expiryYear;
    private int cvc;
    @Column(name = "holder_name")
    private String holderName;
    @Column(name = "default_payment")
    private boolean defaultPayment;

    @Transient
    private MultipartFile cardImage;

    @OneToOne
    @JoinColumn(name = "billing_address")
    private Address billingAddress;

    public OrderPayment() {
    }


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

    public MultipartFile getCardImage() {
        return cardImage;
    }

    public void setCardImage(MultipartFile cardImage) {
        this.cardImage = cardImage;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
