package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.OrderDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.service.CartItemService;
import com.finalproject.ecommercestore.service.OrderService;
import com.finalproject.ecommercestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@ModelAttribute("order") OrderDto orderDto){
       orderService.addOrder(orderDto);
            return "redirect:/user-account";
    }

    @GetMapping("/order-details/{id}")
    public String getOrderDetailsPage(@PathVariable Long id, Model model){
        OrderDto orderDto = orderService.getOrderById(id);
        model.addAttribute("order", orderDto);
        return "order_details";
    }

    @GetMapping("/order-manage/{id}")
    public String getOrderManagePage(@PathVariable Long id, Model model){
        OrderDto orderDto = orderService.getOrderById(id);
        model.addAttribute("order2", orderDto);
        return "order-manage";
    }

    @PostMapping("/order-manage")
    public String orderManage(@ModelAttribute("order2") OrderDto orderDto){
        orderService.save(orderDto);
        return "redirect:/admin-account";
    }
}
