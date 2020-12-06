package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(@Param("userName")String username);

}
