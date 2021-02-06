package com.finalproject.ecommercestore.model.dto;

import com.finalproject.ecommercestore.model.entity.CartItem;
import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.model.entity.Comment;
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
    private boolean active;
    private MultipartFile productImage;
    private List<CategoryDto> productCategories;
    private List<CommentDto> commentDtoList = new ArrayList<>();

    public ProductDto() {
    }

    public List<CommentDto> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }

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

    public boolean getInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }

    public List<CategoryDto> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<CategoryDto> productCategories) {
        this.productCategories = productCategories;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
