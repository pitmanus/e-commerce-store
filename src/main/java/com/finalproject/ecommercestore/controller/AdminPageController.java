package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.dto.OrderDto;
import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.service.CategoryService;
import com.finalproject.ecommercestore.service.OrderService;
import com.finalproject.ecommercestore.service.ProductService;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class AdminPageController {

    private CategoryService categoryService;
    private UserService userService;
    private ProductService productService;
    private OrderService orderService;

    public AdminPageController(CategoryService categoryService, UserService userService, ProductService productService, OrderService orderService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }


   /* @RequestMapping("/products-categories")
    public String productsCategories(Model model){
        model.addAttribute("classActiveCategories", true);
    return "admin-account";
    }*/

    @GetMapping("/admin-account")
    public String showAdminAccountPage(Model model) {
        model.addAttribute("category", new Category());
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("productList", products);
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin-account";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
        return "redirect:/admin-account?categoryadded";
    }

    @PostMapping("/deletecategory")
    public String deleteCategory(@ModelAttribute CategoryDto categoryDto) {
        categoryService.deleteCategory(categoryDto.getId());
        return "redirect:/admin-account?categoryremoved";
    }


    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable Long id) {
        userService.blockUser(id);
        return "redirect:/admin-account";
    }

    @GetMapping("/unblock/{id}")
    public String unBlockUser(@PathVariable Long id) {
        userService.unblockUser(id);
        return "redirect:/admin-account";
    }

    @PostMapping("/deleteuser")
    public String deleteUser(@ModelAttribute UserDto userDto) {
        userService.deleteUser(userDto.getId());
        return "redirect:/admin-account";
    }

    @GetMapping("/newproduct")
    public String showNewProduct(Model model) {
        model.addAttribute("product", new ProductDto());
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        return "new-product";
    }

    @PostMapping("/newproduct")
    public String addNewProduct(@ModelAttribute ProductDto productDto) {
        productService.addNewProduct(productDto);
        return "redirect:/admin-account";
    }

    @PostMapping("/deleteproduct")
    public String deleteProduct(@ModelAttribute ProductDto productDto) {
        productService.deleteProduct(productDto.getId());
        return "redirect:/admin-account";
    }

    @GetMapping("/editproduct/{id}")
    public String showEditProductPage(@PathVariable Long id, Model model) {
        ProductDto product = productService.getById(id);
        model.addAttribute("selectedProduct", product);
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        return "editproduct";
    }

    @PostMapping("/editproduct")
    public String editProduct(@ModelAttribute("selectedProduct") ProductDto productDto) {
        productService.editProduct(productDto);
        return "redirect:/admin-account";
    }

}
