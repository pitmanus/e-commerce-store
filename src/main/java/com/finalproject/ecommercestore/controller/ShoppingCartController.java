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
        List<CartItemDto> cartItemDtoList = shoppingCart.getCartItemList();
        model.addAttribute("cartItems", cartItemDtoList);
        return "shopping-cart";
    }

    @PostMapping("/delete-cart-item")
    public String deleteCartItem(@ModelAttribute CartItemDto cartItemDto, Model model) {
        shoppingCartService.deleteCartItem(cartItemDto.getId());
        ShoppingCartDto shoppingCart = shoppingCartService.getShoppingCart();
        model.addAttribute("shoppingCart", shoppingCart);
        List<CartItemDto> cartItemDtoList = shoppingCart.getCartItemList();
        model.addAttribute("cartItems", cartItemDtoList);
        return "redirect:/shopping-cart";
    }
}
