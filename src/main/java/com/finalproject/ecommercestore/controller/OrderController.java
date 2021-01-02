package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.OrderDto;
import com.finalproject.ecommercestore.service.CartItemService;
import com.finalproject.ecommercestore.service.OrderService;
import com.finalproject.ecommercestore.service.UserService;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    private OrderService orderService;
    private CartItemService cartItemService;
    private UserService userService;
    private ModelMapper modelMapper;

    public OrderController(OrderService orderService, CartItemService cartItemService, UserService userService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/order")
    public String showOrderPage(Model model){
        OrderDto orderDto = new OrderDto();
        model.addAttribute("order", orderDto);
        model.addAttribute("user", userService.getLoggedUser());
        return "order";
    }

}
