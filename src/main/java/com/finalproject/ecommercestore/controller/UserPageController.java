package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.AddressDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserPageController {

    private UserService userService;


    public UserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-account")
    public String showUserAccountPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserDto userDto = userService.getUserByUserName(userName);
        model.addAttribute("user", userDto);
        return "user-account";
    }

    @GetMapping("/edit-user-info/{id}")
    public String showEditUserPage(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getById(id);
        model.addAttribute("user", userDto);
        model.addAttribute("address", userDto.getAddress());
        return "edit-user-info";
    }

    @PostMapping("/edit-user/{id}")
    public String editUserInformation(@PathVariable Long id, @Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, @Valid @ModelAttribute("address") AddressDto addressDto, BindingResult bindingResult2) {
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "redirect:/edit-user-info/"+id;
        } else {
            userDto.setAddress(addressDto);
            userService.fullUserUpdate(userDto);
            return "redirect:/user-account";
        }
    }
}


