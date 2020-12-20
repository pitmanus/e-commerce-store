package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.CartItemDto;
import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.model.entity.CartItem;
import com.finalproject.ecommercestore.model.entity.Product;
import com.finalproject.ecommercestore.model.entity.ShoppingCart;
import com.finalproject.ecommercestore.model.entity.User;
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
    private ProductService productService;
    private CartItemService cartItemService;

    public String getUserNameFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper, UserService userService, ProductService productService, CartItemService cartItemService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    public ShoppingCartDto getShoppingCart(){
        return userService.getUserDtoByUserName(getUserNameFromSecurityContext()).getShoppingCart();
    }

    public void deleteCartItem(Long id){
        cartItemService.deleteCartItem(id);
    }
}
