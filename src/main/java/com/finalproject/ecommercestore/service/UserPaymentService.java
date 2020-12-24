package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.entity.UserPayment;
import com.finalproject.ecommercestore.repository.UserPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserPaymentService {
    private UserPaymentRepository userPaymentRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public UserPaymentService(UserPaymentRepository userPaymentRepository, ModelMapper modelMapper, UserService userService) {
        this.userPaymentRepository = userPaymentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


}
