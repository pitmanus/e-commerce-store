package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
