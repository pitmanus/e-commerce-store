package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.repository.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper, UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public ShoppingCartDto getShoppingCart(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userService.getUserByUserName(userName).getShoppingCart();
    }
}
