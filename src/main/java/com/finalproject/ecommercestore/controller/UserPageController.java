package com.finalproject.ecommercestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserPageController {

    @GetMapping("/user-account")
    public String showUserAccountPage(){
        return "user-account";
    }
}
