package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CartItemDto;
import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.model.entity.ShoppingCart;
import com.finalproject.ecommercestore.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/shopping-cart")
    public String showShoppingCart(Model model){
        ShoppingCartDto shoppingCart = shoppingCartService.getShoppingCart();
        model.addAttribute("shoppingCart", shoppingCart);
        List<CartItemDto> cartItemDtoList = shoppingCart.getCartItemList();
        model.addAttribute("cartItems", cartItemDtoList);
        return "shopping-cart";
    }
}
