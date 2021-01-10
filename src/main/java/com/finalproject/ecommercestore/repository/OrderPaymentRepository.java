package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, Long> {

}
