package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.CommentDto;
import com.finalproject.ecommercestore.model.entity.Comment;
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

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    public void addComment(CommentDto commentDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        commentDto.setAuthor(userName);
        commentDto.setTime(LocalDateTime.now());
        commentRepository.save(modelMapper.map(commentDto, Comment.class));
    }

    public List<CommentDto> getAllCommentsForASingleProduct(Long id){
        return commentRepository.findAll()
                .stream()
                .filter(comment -> comment.getProducts().stream().anyMatch(product -> product.getId()==id))
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}

