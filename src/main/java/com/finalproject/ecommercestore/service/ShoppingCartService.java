package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.CartItemDto;
import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.model.entity.CartItem;
import com.finalproject.ecommercestore.model.entity.Product;
import com.finalproject.ecommercestore.model.entity.ShoppingCart;
import com.finalproject.ecommercestore.model.entity.User;
import com.finalproject.ecommercestore.repository.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@Service
public class ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private ProductService productService;
    private CartItemService cartItemService;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

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

    public void saveShoppingCart(ShoppingCartDto shoppingCartDto){
        shoppingCartRepository.save(modelMapper.map(shoppingCartDto, ShoppingCart.class));
    }

    public void setTotalPriceOfShoppingCartWhileDeletingCartItem(BigDecimal bigDecimal){
        ShoppingCartDto shoppingCartDto = getShoppingCart();
        shoppingCartDto.setTotal(shoppingCartDto.getTotal().subtract(bigDecimal));
        saveShoppingCart(shoppingCartDto);
    }

    public void deleteCartItem(Long id){
        cartItemService.deleteCartItem(id);
    }

    public void deleteAllItems(Long id){
        shoppingCartRepository.deleteById(id);
    }
}
