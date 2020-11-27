package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminPageController {

    private CategoryService categoryService;

    public AdminPageController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


   /* @RequestMapping("/products-categories")
    public String productsCategories(Model model){
        model.addAttribute("classActiveCategories", true);
    return "admin-account";
    }*/

    @GetMapping("/admin-account")
    public String showAdminAccountPage(Model model){
        model.addAttribute("category", new Category());
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        return "admin-account";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category")CategoryDto categoryDto, Model model){
        categoryService.addCategory(categoryDto);
        return "redirect:/admin-account?categoryadded";
    }

    @PostMapping("/deletecategory")
    public String deleteCategory(@ModelAttribute CategoryDto categoryDto, Model model) {
        categoryService.deleteCategory(categoryDto.getId());
        return "redirect:/admin-account?categoryremoved";
    }


}
