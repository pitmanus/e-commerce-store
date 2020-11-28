package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.model.entity.User;
import com.finalproject.ecommercestore.service.CategoryService;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class AdminPageController {

    private CategoryService categoryService;

    private UserService userService;

    public AdminPageController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
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
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
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


    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable Long id){
        UserDto  userDto = userService.getById(id);
        userDto.setEnabled(false);
        userService.updateUser(userDto);
        return "redirect:/admin-account";
    }

    @GetMapping("/unblock/{id}")
    public String unBlockUser(@PathVariable Long id){
        UserDto  userDto = userService.getById(id);
        userDto.setEnabled(true);
        userService.updateUser(userDto);
        return "redirect:/admin-account";
    }

    @PostMapping("/deleteuser")
        String deleteUser(@ModelAttribute UserDto userDto){
        userService.deleteUser(userDto.getId());
        return "redirect:/admin-account";
    }


}
