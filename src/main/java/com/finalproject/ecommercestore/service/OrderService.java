package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;


}
