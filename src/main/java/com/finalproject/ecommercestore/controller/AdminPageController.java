package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.entity.Category;
import com.finalproject.ecommercestore.model.entity.Product;
import com.finalproject.ecommercestore.model.entity.User;
import com.finalproject.ecommercestore.service.CategoryService;
import com.finalproject.ecommercestore.service.ProductService;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Controller
public class AdminPageController {

    private CategoryService categoryService;
    private UserService userService;
    private ProductService productService;

    public AdminPageController(CategoryService categoryService, UserService userService, ProductService productService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
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
        return "admin-account";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
        return "redirect:/admin-account?categoryadded";
    }

    @PostMapping("/deletecategory")
    public String deleteCategory(@ModelAttribute CategoryDto categoryDto, Model model) {
        categoryService.deleteCategory(categoryDto.getId());
        return "redirect:/admin-account?categoryremoved";
    }


    @GetMapping("/block/{id}")
    public String blockUser(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        userDto.setEnabled(false);
        userService.updateUser(userDto);
        return "redirect:/admin-account";
    }

    @GetMapping("/unblock/{id}")
    public String unBlockUser(@PathVariable Long id) {
        UserDto userDto = userService.getById(id);
        userDto.setEnabled(true);
        userService.updateUser(userDto);
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

    @GetMapping("/productlist")
    public String showAllProducts(Model model){
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("productList", products);
        return "productlist";
    }

    @PostMapping("/newproduct")
    public String addNewProduct(@ModelAttribute ProductDto productDto) {
       Product product = productService.addProduct(productDto);

        MultipartFile productImage = productDto.getProductImage();

        try {
            byte[] bytes = productImage.getBytes();
            String name = product.getId()+".png";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/product" + name)));
            stream.write(bytes);
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/productlist";
    }

    @PostMapping("/deleteproduct")
    public String deleteProduct(@ModelAttribute ProductDto productDto){
        productService.deleteProduct(productDto.getId());
        return "redirect:/productlist";
    }


}
