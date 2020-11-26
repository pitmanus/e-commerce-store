package com.finalproject.ecommercestore.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @GetMapping({"/index", "/"})
    public String mainPage(){
        return "index";
    }

    /*@GetMapping("/my-account")
    public String myAccount(){
        return "my-account";
    }*/

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/my-account")
    public void myAccountPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult)throws IOException, ServletException {
        String role = authResult.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin-account"));
        }
        else if (role.contains("USER")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user-account"));
        }
    }




}

