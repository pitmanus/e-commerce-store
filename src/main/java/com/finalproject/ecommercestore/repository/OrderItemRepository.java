package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
