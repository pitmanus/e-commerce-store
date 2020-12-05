package com.finalproject.ecommercestore.repository;
import com.finalproject.ecommercestore.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
