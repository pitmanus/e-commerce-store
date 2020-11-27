package com.finalproject.ecommercestore.repository;

import com.finalproject.ecommercestore.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
