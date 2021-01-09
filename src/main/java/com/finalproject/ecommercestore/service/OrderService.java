package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.OrderDto;
import com.finalproject.ecommercestore.model.dto.ShoppingCartDto;
import com.finalproject.ecommercestore.model.entity.*;
import com.finalproject.ecommercestore.repository.OrderItemRepository;
import com.finalproject.ecommercestore.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ModelMapper modelMapper;
    private CartItemService cartItemService;
    private UserService userService;
    private ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper, CartItemService cartItemService, UserService userService, ShoppingCartService shoppingCartService) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Transactional
    public void addOrder(OrderDto orderDto){
        User user = modelMapper.map(userService.getLoggedUser(), User.class);
        Order order = modelMapper.map(orderDto, Order.class);
        LocalDateTime now = LocalDateTime.now();
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

        List<OrderItem> orderItems = user.getShoppingCart().getCartItemList().stream()
                .map(cartItem -> new OrderItem(cartItem.getProduct(), cartItem.getQuantity(), cartItem.getSubtotal())).
                        collect(Collectors.toList());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderItems.forEach(orderItem -> orderItemRepository.save(orderItem));

        order.setOrderTotal(user.getShoppingCart().getTotal());
        order.setShippingAddress(user.getAddress());
        order.setOrderItems(orderItems);
        user.getShoppingCart().setCartItemList(new ArrayList<>());
        shoppingCartService.deleteAllItems(user.getShoppingCart().getId());
        user.getOrderList().add(order);
        user.getShoppingCart().setTotal(BigDecimal.ZERO);
        userService.save(user);

    }

    public List<OrderDto> getAllUserOrders(){
        return userService.getLoggedUser().getOrderList();
    }

    public OrderDto getOrderById(Long id){
        return modelMapper.map(orderRepository.findById(id).get(), OrderDto.class);
    }
}
