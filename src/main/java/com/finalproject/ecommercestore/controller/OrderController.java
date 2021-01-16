package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.OrderDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.service.CartItemService;
import com.finalproject.ecommercestore.service.OrderService;
import com.finalproject.ecommercestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @ModelAttribute("user")
    public UserDto userInViews(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        GrantedAuthority authority = auth.getAuthorities().stream().findFirst().get();
        System.out.println(authority.getAuthority());
        if (auth.getPrincipal().equals("anonymousUser")||authority.getAuthority().equals("ROLE_ADMIN")){
            return null;
        }
        return userService.getLoggedUser();
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

    @PostMapping("/deleteorder")
    public String deleteOrder(@ModelAttribute OrderDto orderDto){
        orderService.deleteOrder(orderDto.getId());
        return "redirect:/admin-account";
    }

}
