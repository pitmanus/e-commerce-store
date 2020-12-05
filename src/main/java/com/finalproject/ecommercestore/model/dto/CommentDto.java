package com.finalproject.ecommercestore.model.dto;

import com.finalproject.ecommercestore.model.entity.Product;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class CommentDto {
    private Long id;
    private String text;
    private String author;
    private LocalDateTime time;
    private ProductDto product;

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}


