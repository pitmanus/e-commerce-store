package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {
}
