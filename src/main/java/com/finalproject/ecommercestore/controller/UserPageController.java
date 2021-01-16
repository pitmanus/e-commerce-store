package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.*;
import com.finalproject.ecommercestore.service.OrderService;
import com.finalproject.ecommercestore.service.UserPaymentService;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserPageController {

    private UserService userService;
    private UserPaymentService userPaymentService;
    private OrderService orderService;


    public UserPageController(UserService userService, UserPaymentService userPaymentService, OrderService orderService) {
        this.userService = userService;
        this.userPaymentService = userPaymentService;
        this.orderService = orderService;
    }

    @ModelAttribute("user")
    public UserDto userInViews(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        GrantedAuthority authority = auth.getAuthorities().stream().findFirst().get();
        System.out.println(authority.getAuthority());
        if (auth.getPrincipal().equals("anonymousUser")||authority.getAuthority().equals("ROLE_ADMIN")){
            return null;
        }
        return userService.getLoggedUser();
    }

    @GetMapping("/user-account")
    public String showUserAccountPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserDto userDto = userService.getUserDtoByUserName(userName);
        model.addAttribute("user", userDto);
        List<UserPaymentDto> userPaymentDtoList = userPaymentService.getAllUserPaymentCards();
        model.addAttribute("paymentCards", userPaymentDtoList);
        List<OrderDto> userOrders = orderService.getAllUserOrders();
        model.addAttribute("userOrders", userOrders);
        return "user-account";
    }

    @GetMapping("/edit-user-info/{id}")
    public String showEditUserPage(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getById(id);
        model.addAttribute("user", userDto);
        model.addAttribute("address", userDto.getAddress());
        return "edit-user-info";
    }

    @PostMapping("/edit-user")
    public String editUserInformation(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, @Valid @ModelAttribute("address") AddressDto addressDto, BindingResult bindingResult2) {
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "edit-user-info";
        } else {
            userService.editUser(userDto, addressDto);
            return "redirect:/user-account";
        }
    }

    @GetMapping("/new-payment-card")
    public String showNewPaymentCardPage(Model model) {
        UserPaymentDto userPaymentDto = new UserPaymentDto();
        model.addAttribute("paymentCard", userPaymentDto);
        return "new-payment-card";
    }

    @PostMapping("/new-payment-card")
    public String addNewPaymentCard(@ModelAttribute UserPaymentDto userPaymentDto){
        userPaymentService.addUserPayment(userPaymentDto);

        return "redirect:/user-account";
    }

    @PostMapping("/deletecard")
    public String deleteCard(@ModelAttribute UserPaymentDto userPaymentDto){
        userPaymentService.deletePaymentCard(userPaymentDto.getId());
        return "redirect:/user-account";
    }

    @GetMapping("/edit-payment-card/{id}")
    public String showEditPaymentCardPage(@PathVariable Long id, Model model){
        UserPaymentDto userPaymentDto = userPaymentService.getUserPaymentCardById(id);
        model.addAttribute("userPaymentCard", userPaymentDto);
        return "edit-payment-card";
    }

    @PostMapping("/edit-payment-card")
    public String editPaymentCard(@ModelAttribute("userPaymentCard") UserPaymentDto userPaymentDto) {

        userPaymentService.editUserPayment(userPaymentDto);

        return "redirect:/user-account";
    }

    @GetMapping("/card-details/{id}")
    public String getCardDetailsPage(@PathVariable Long id, Model model){
        UserPaymentDto userPaymentDto = userPaymentService.getUserPaymentCardById(id);
        model.addAttribute("paymentCard", userPaymentDto);
        return "card-details";
    }


}


