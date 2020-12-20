package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.CommentDto;
import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.model.entity.Comment;
import com.finalproject.ecommercestore.model.entity.Product;
import com.finalproject.ecommercestore.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private ProductService productService;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, ProductService productService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    public void addComment(CommentDto commentDto, Long productId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        Product product = productService.getProductById(productId);
        commentDto.setAuthor(userName);
        commentDto.setTime(LocalDateTime.now());
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setId(null);
        product.getComments().add(comment);
        comment.setProduct(product);
        productService.save(product);
    }

    public List<CommentDto> getAllComments(){
        return commentRepository.findAll()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForASingleProduct(Long id){
        return commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getProduct().getId()==id)
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}

