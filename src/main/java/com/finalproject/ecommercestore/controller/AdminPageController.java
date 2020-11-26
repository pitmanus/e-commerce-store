package com.finalproject.ecommercestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin-account")
    public String showAdminAccountPage(){
        return "admin-account";
    }
}
