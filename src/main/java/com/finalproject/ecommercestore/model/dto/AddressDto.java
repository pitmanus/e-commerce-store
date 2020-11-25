package com.finalproject.ecommercestore.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressDto {
    private Long id;
    @NotNull
    @Size(min = 3, message = "Invalid street name!")
    private String street;
    @NotNull
    private String buildingNumberAndApartment;
    @NotNull
    @Size(min = 3, message = "Invalid city name!")
    private String city;
    @NotNull
    @Size(min = 3, message = "Invalid zip code!")
    private String zip;

    public AddressDto(String street, String buildingNumberAndApartment, String city, String zip) {
        this.street = street;
        this.buildingNumberAndApartment = buildingNumberAndApartment;
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

    public String getBuildingNumberAndApartment() {
        return buildingNumberAndApartment;
    }

    public void setBuildingNumberAndApartment(String buildingNumberAndApartment) {
        this.buildingNumberAndApartment = buildingNumberAndApartment;
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
