package com.finalproject.ecommercestore.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressDto {
    private Long id;
    @NotNull
    @Size(min = 3, message = "Invalid street name!")
    private String street;
    @NotNull
    @NotEmpty
    @Size(min = 1, message = "Please enter building number")
    private String buildingNumber;
    private String apartmentNumber;
    @NotNull
    @Size(min = 3, message = "Invalid city name!")
    private String city;
    @NotNull
    @Size(min = 3, message = "Invalid zip code!")
    private String zip;

    public AddressDto(String street, String buildingNumber, String apartmentNumber, String city, String zip) {
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.zip = zip;
    }

    public AddressDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
