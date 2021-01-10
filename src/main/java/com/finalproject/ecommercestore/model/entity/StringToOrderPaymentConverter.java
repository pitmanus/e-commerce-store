package com.finalproject.ecommercestore.model.entity;

import com.finalproject.ecommercestore.model.entity.OrderPayment;
import com.finalproject.ecommercestore.repository.OrderPaymentRepository;
import com.finalproject.ecommercestore.repository.UserPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringToOrderPaymentConverter implements Converter<String, OrderPayment> {
    private OrderPaymentRepository orderPaymentRepository;
    private UserPaymentRepository userPaymentRepository;
    private ModelMapper modelMapper;
    private List<OrderPayment> orderPayments = new ArrayList<>();

    public StringToOrderPaymentConverter (OrderPaymentRepository orderPaymentRepository, UserPaymentRepository userPaymentRepository, ModelMapper modelMapper) {
        this.orderPaymentRepository = orderPaymentRepository;
        this.userPaymentRepository = userPaymentRepository;
        this.modelMapper = modelMapper;

        List<OrderPayment> paymentsList = userPaymentRepository.findAll().stream()
                .map(payment -> modelMapper.map(payment, OrderPayment.class))
                .collect(Collectors.toList());

        paymentsList.forEach(orderPayments::add);

    }

    @Override
    public OrderPayment convert(String id) {
        if(id.equals(""))
            return null;

        long parsedId = Long.parseLong(id);
        return orderPayments
                .stream()
                .filter(payment -> payment.getId().equals(parsedId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order payment with id: " + parsedId + " not found"));
    }
}
