package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.configuration.SecurityConfiguration;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserPageController {

    private UserService userService;


    public UserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-account")
    public String showUserAccountPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserDto userDto = userService.getUserByUserName(userName);
        model.addAttribute("user", userDto);
        return "user-account";
    }

}
