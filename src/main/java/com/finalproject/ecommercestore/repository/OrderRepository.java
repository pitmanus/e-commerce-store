package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
