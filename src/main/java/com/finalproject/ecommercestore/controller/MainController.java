package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.CategoryDto;
import com.finalproject.ecommercestore.model.dto.CommentDto;
import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.service.CategoryService;
import com.finalproject.ecommercestore.service.CommentService;
import com.finalproject.ecommercestore.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private CategoryService categoryService;
    private ProductService productService;
    private CommentService commentService;

    public MainController(CategoryService categoryService, ProductService productService, CommentService commentService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.commentService = commentService;
    }

    @GetMapping({"/index", "/"})
    public String mainPage(Model model) {
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<ProductDto> productList = productService.getAllProducts();
        model.addAttribute("productlist", productList);
        return "index";
    }

    @GetMapping("/products/{id}")
    public String showFilteredByCategoryProducts(@PathVariable Long id, Model model) {
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<ProductDto> productList = productService.getProductsByCategories(id);
        model.addAttribute("productlist", productList);
        return "filtered-products";
    }

    @RequestMapping(value = "search-products", method = RequestMethod.GET)
    public String showFilteredByProductName(@RequestParam(value = "productName", required = false, defaultValue = "") String productName, Model model) {
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<ProductDto> productList = productService.getProductsByProductName(productName);
        model.addAttribute("productlist", productList);
        return "filtered-products";
    }

    @GetMapping("/product-page/{id}")
    public String showSingleProductPage(@PathVariable Long id, Model model){
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        ProductDto product = productService.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("comment", new CommentDto());
        List<CommentDto> comments = commentService.getAllComments();
        model.addAttribute("comments", comments);
        return "product-page";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/my-account")
    public void myAccountPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        String role = authResult.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin-account"));
        } else if (role.contains("ROLE_USER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user-account"));
        }
    }


}

