package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
