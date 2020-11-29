package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.service.CategoryService;
import com.finalproject.ecommercestore.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private CategoryService categoryService;
    private ProductService productService;

    public MainController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/index", "/"})
    public String mainPage(Model model){
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<ProductDto> productList = productService.getAllProducts();
        model.addAttribute("productlist", productList);
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
        else if (role.contains("ROLE_USER")){
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user-account"));
        }
    }




}

