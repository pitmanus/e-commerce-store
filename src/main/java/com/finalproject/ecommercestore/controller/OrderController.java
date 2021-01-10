package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.OrderDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.dto.UserPaymentDto;
import com.finalproject.ecommercestore.model.entity.Order;
import com.finalproject.ecommercestore.service.CartItemService;
import com.finalproject.ecommercestore.service.OrderService;
import com.finalproject.ecommercestore.service.UserService;
import org.dom4j.rule.Mode;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        UserDto userDto = userService.getLoggedUser();
        model.addAttribute("user", userDto);
        UserPaymentDto userPaymentDto = new UserPaymentDto();
        model.addAttribute("userPayment", userPaymentDto);
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@ModelAttribute("order") OrderDto orderDto, @ModelAttribute("userPayment") UserPaymentDto userPaymentDto){
       orderService.addOrder(orderDto);
            return "redirect:/user-account";
    }
}
