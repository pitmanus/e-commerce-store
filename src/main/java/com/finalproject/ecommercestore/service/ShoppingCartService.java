package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.CartItemDto;
import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.model.entity.*;
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

    public void addItemToShoppingCart(Long id, CartItemDto cartItemDto){
        ProductDto productDto = productService.getById(id);
        cartItemDto.setProduct(productDto);
        cartItemDto.setSubtotal(cartItemService.getSubTotal(productDto.getPrice(), cartItemDto.getQuantity()));
        cartItemDto.setShoppingCart(getShoppingCart());
        ShoppingCartDto shoppingCartDto = getShoppingCart();
        shoppingCartDto.getCartItemList().add(cartItemDto);
        shoppingCartDto.setTotal(shoppingCartDto
                .getCartItemList()
                .stream()
                .map(item->item.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        saveShoppingCart(shoppingCartDto);
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

    public void deleteAllItems(Long id){
        shoppingCartRepository.deleteById(id);
    }
}
