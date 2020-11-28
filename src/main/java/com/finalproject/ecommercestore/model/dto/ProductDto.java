package com.finalproject.ecommercestore.model.dto;

import com.finalproject.ecommercestore.model.entity.CartItem;
import com.finalproject.ecommercestore.model.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private double shippingWeight;
    private boolean inStock = false;
    private MultipartFile productImage;
    private CartItem cartItem;
    private List<Category> productCategories = new ArrayList<>();

    public ProductDto() {
    }

    /*public ProductDto(String name, BigDecimal price, String description, double shippingWeight, boolean inStock, MultipartFile productImage, CartItem cartItem, List<Category> productCategories) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.shippingWeight = shippingWeight;
        this.inStock = inStock;
        this.productImage = productImage;
        this.cartItem = cartItem;
        this.productCategories = productCategories;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public List<Category> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Category> productCategories) {
        this.productCategories = productCategories;
    }
}
