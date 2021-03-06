package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CartItemDto;
import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.entity.CartItem;
import com.finalproject.ecommercestore.service.CartItemService;
import com.finalproject.ecommercestore.service.ProductService;
import com.finalproject.ecommercestore.service.ShoppingCartService;
import com.finalproject.ecommercestore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;
    private CartItemService cartItemService;
    private ProductService productService;
    private UserService userService;
    private ModelMapper modelMapper;

    public ShoppingCartController(ShoppingCartService shoppingCartService, CartItemService cartItemService, ProductService productService, UserService userService,
                                  ModelMapper modelMapper) {
        this.shoppingCartService = shoppingCartService;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("user")
    public UserDto userInViews(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority authority = auth.getAuthorities().stream().findFirst().get();
        if (auth.getPrincipal().equals("anonymousUser")||authority.getAuthority().equals("ROLE_ADMIN")){
            return null;
        }
        return userService.getLoggedUser();
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
        cartItemService.delete(modelMapper.map(cartItemDto, CartItem.class));
        shoppingCartService.setTotalPriceOfShoppingCartWhileDeletingCartItem(cartItemDto.getSubtotal());
        return "redirect:/shopping-cart";
    }
}
