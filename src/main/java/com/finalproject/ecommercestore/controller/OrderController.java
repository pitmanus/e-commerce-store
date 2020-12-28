package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.service.CartItemService;
import com.finalproject.ecommercestore.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
public class OrderController {

    private OrderService orderService;
    private CartItemService cartItemService;
    private ModelMapper modelMapper;

    public OrderController(OrderService orderService, CartItemService cartItemService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.modelMapper = modelMapper;
    }


}
