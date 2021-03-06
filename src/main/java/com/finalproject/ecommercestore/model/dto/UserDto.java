package com.finalproject.ecommercestore.model.dto;
import com.finalproject.ecommercestore.model.entity.Order;
import com.finalproject.ecommercestore.model.entity.Role;
import com.finalproject.ecommercestore.model.entity.ShoppingCart;
import com.finalproject.ecommercestore.model.entity.UserPayment;
import org.hibernate.Hibernate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private Long id;
    @NotNull
    @Size(min = 2, max = 30, message = "Length must be from 2 to 30 letters")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "Length must be from 2 to 30 letters")
    private String username;

    @NotNull
    @Size(min = 2, max = 30, message = "Length must be from 2 to 30 letters")
    private String lastName;

    @NotNull
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Invalid email address!")
    private String email;

    @NotNull
    @Size(min = 9, message = "Invalid phone number!")
    private String tel;

    private Boolean enabled = true;

    @NotNull
    @Pattern(regexp = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$", message = "Password should contain at least one number, one letter, and be between 6-15 characters in length!")
    private String password;

    private List<Role> roles = new ArrayList<>();

    private AddressDto address;

    private ShoppingCartDto shoppingCart;

    private List<OrderDto> orderList = new ArrayList<>();

    private List<UserPaymentDto> userPayments = new ArrayList<>();

    private MultipartFile accountImage;


    public UserDto() {
    }

    public List<UserPaymentDto> getUserPayments() {
        return userPayments;
    }

    public void setUserPayments(List<UserPaymentDto> userPayments) {
        this.userPayments = userPayments;
    }

    public List<OrderDto> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDto> orderList) {
        this.orderList = orderList;
    }

    public ShoppingCartDto getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartDto shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public MultipartFile getAccountImage() {
        return accountImage;
    }

    public void setAccountImage(MultipartFile accountImage) {
        this.accountImage = accountImage;
    }


}
