package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.CommentDto;
import com.finalproject.ecommercestore.model.entity.Comment;
import com.finalproject.ecommercestore.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    public void addComment(CommentDto commentDto){
        commentDto.setTime(LocalDateTime.now());
        commentRepository.save(modelMapper.map(commentDto, Comment.class));
    }
}

