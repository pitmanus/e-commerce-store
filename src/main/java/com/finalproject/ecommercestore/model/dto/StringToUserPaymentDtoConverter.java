package com.finalproject.ecommercestore.model.dto;

import com.finalproject.ecommercestore.repository.UserPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringToUserPaymentDtoConverter implements Converter<String, UserPaymentDto> {
    private UserPaymentRepository userPaymentRepository;
    private ModelMapper modelMapper;
    private List<UserPaymentDto> userPayments = new ArrayList<>();

    public StringToUserPaymentDtoConverter(UserPaymentRepository userPaymentRepository, ModelMapper modelMapper) {
        this.userPaymentRepository = userPaymentRepository;
        this.modelMapper = modelMapper;

        List<UserPaymentDto> paymentsList = userPaymentRepository.findAll().stream()
                .map(payment -> modelMapper.map(payment, UserPaymentDto.class))
                .collect(Collectors.toList());
        paymentsList.forEach(userPayments::add);

    }

    @Override
    public UserPaymentDto convert(String id) {
        if(id.equals(""))
            return null;

        long parsedId = Long.parseLong(id);
        return userPayments
                .stream()
                .filter(payment -> payment.getId().equals(parsedId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User payment with id: " + parsedId + " not found"));
    }
}
