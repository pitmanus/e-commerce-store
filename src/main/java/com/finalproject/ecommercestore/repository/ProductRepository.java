package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
