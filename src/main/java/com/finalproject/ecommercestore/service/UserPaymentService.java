package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.UserPaymentDto;
import com.finalproject.ecommercestore.model.entity.User;
import com.finalproject.ecommercestore.model.entity.UserPayment;
import com.finalproject.ecommercestore.repository.UserPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPaymentService {
    private UserPaymentRepository userPaymentRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public UserPaymentService(UserPaymentRepository userPaymentRepository, ModelMapper modelMapper, UserService userService) {
        this.userPaymentRepository = userPaymentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public String getUserNameFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public UserPayment saveUserPayment(UserPaymentDto userPaymentDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.getUserByUserName(name);
        UserPayment userPayment = modelMapper.map(userPaymentDto, UserPayment.class);
        userPayment.setUser(user);
        userPayment.setBillingAddress(user.getAddress());
        user.getUserPayments().add(userPayment);
        userService.save(user);
        return user.getUserPayments().stream()
                .filter(payment -> payment.getCardNumber().equals(userPaymentDto.getCardNumber()))
                .findFirst().get();
    }

    public List<UserPaymentDto> getAllUserPaymentCards(){
        return userService.getUserDtoByUserName(getUserNameFromSecurityContext()).getUserPayments();
    }

    public UserPaymentDto getUserPaymentCardById(Long id){
       return modelMapper.map(userPaymentRepository.findById(id).get(), UserPaymentDto.class);
    }

    public void deletePaymentCard(Long id){
        userPaymentRepository.deleteById(id);
    }

}
