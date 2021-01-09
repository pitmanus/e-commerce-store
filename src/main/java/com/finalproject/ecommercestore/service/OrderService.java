package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.OrderDto;
import com.finalproject.ecommercestore.model.entity.*;
import com.finalproject.ecommercestore.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private CartItemService cartItemService;
    private UserService userService;
    private ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, CartItemService cartItemService, UserService userService, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    public void addOrder(OrderDto orderDto){
        LocalDateTime now = LocalDateTime.now();
        User user = modelMapper.map(userService.getLoggedUser(), User.class);
        Order order = modelMapper.map(orderDto, Order.class);
        order.setOrderDate(now);
        order.setOrderStatus(OrderStatus.AWAITING_SHIPMENT);
        order.setUser(user);
        if(orderDto.getShippingMethod().equals(ShippingMethod.STANDART_SHIPPING)){
            order.setShippingDate(now.plusDays(5));
        }else if (orderDto.getShippingMethod().equals(ShippingMethod.FEDEX)){
            order.setShippingDate(now.plusDays(4));
        }else if(orderDto.getShippingMethod().equals(ShippingMethod.DHL)){
            order.setShippingDate(now.plusDays(3));
        }else if (orderDto.getShippingMethod().equals(ShippingMethod.UPS)){
            order.setShippingDate(now.plusDays(2));
        }
        order.setCartItems(user.getShoppingCart().getCartItemList());
        order.setOrderTotal(user.getShoppingCart().getTotal());
        order.setShippingAddress(user.getAddress());
        user.getShoppingCart().getCartItemList().forEach(item->item.setOrder(order));
        user.getOrderList().add(order);
        user.getShoppingCart().setTotal(BigDecimal.ZERO);
        userService.save(user);
    }

    public List<OrderDto> getAllUserOrders(){
        return userService.getLoggedUser().getOrderList();
    }
}
