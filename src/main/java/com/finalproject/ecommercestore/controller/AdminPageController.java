package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminPageController {

    private CategoryService categoryService;

    public AdminPageController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin-account")
    public String showAdminAccountPage(Model model){
        model.addAttribute("category", new Category());
        return "admin-account";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category")CategoryDto categoryDto){
        categoryService.addCategory(categoryDto);
        return "redirect:/admin-account?categoryadded";
    }


}
