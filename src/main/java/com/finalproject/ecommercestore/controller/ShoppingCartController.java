package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CartItemDto;
import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.model.entity.ShoppingCart;
import com.finalproject.ecommercestore.service.CartItemService;
import com.finalproject.ecommercestore.service.ProductService;
import com.finalproject.ecommercestore.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;
    private CartItemService cartItemService;
    private ProductService productService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, CartItemService cartItemService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @GetMapping("/shopping-cart")
    public String showShoppingCart(Model model) {
        ShoppingCartDto shoppingCart = shoppingCartService.getShoppingCart();
        model.addAttribute("shoppingCart", shoppingCart);
        if (shoppingCart.getCartItemList().isEmpty()){
            return "empty-shopping-cart";
        }else{
            return "shopping-cart";
        }

    }

    @PostMapping("/delete-cart-item")
    public String deleteCartItem(@ModelAttribute CartItemDto cartItemDto) {
        shoppingCartService.deleteCartItem(cartItemDto.getId());
        shoppingCartService.setTotalPriceOfShoppingCartWhileDeletingCartItem(cartItemDto.getSubtotal());
        return "redirect:/shopping-cart";
    }
}
