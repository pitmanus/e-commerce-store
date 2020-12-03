package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
