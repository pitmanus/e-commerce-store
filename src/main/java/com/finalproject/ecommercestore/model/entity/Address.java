package com.finalproject.ecommercestore.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    @Column(name = "building_number_and_apartment")
    private String buildingNumberAndApartment;
    private String city;
    private String zip;

    public Address(String street, String buildingNumberAndApartment, String city, String zip) {
        this.street = street;
        this.buildingNumberAndApartment = buildingNumberAndApartment;
        this.city = city;
        this.zip = zip;
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
