package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.*;
import com.finalproject.ecommercestore.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private CategoryService categoryService;
    private ProductService productService;
    private CommentService commentService;
    private ShoppingCartService shoppingCartService;
    private UserService userService;

    public MainController(CategoryService categoryService, ProductService productService, CommentService commentService, ShoppingCartService shoppingCartService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.commentService = commentService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
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
    public String showSingleProductPage(@PathVariable Long id, Model model) {
        if (userService.getLoggedUser().getEnabled()==true){
            List<CategoryDto> categories = categoryService.showAllCategories();
            model.addAttribute("categories", categories);
            ProductDto product = productService.getById(id);
            model.addAttribute("product", product);
            CommentDto commentDto = new CommentDto();
            model.addAttribute("comment", commentDto);
            List<CommentDto> comments = commentService.getAllCommentsForASingleProduct(id);
            model.addAttribute("comments", comments);
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setQuantity(1);
            model.addAttribute("cartItem", cartItemDto);
            return "product-page";
        }
        else {
            return "banned-user";
        }
    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart( @Valid @ModelAttribute("cartItem") CartItemDto cartItemDto, BindingResult bindingResult, @PathVariable Long id, Model model) {
        if (bindingResult.hasErrors()){
            List<CategoryDto> categories = categoryService.showAllCategories();
            model.addAttribute("categories", categories);
            ProductDto productDto = productService.getById(id);
            model.addAttribute("product", productDto);
            CommentDto commentDto = new CommentDto();
            model.addAttribute("comment", commentDto);
            List<CommentDto> comments = commentService.getAllCommentsForASingleProduct(id);
            model.addAttribute("comments", comments);
            return "product-page";
        }else{
            shoppingCartService.addItemToShoppingCart(id, cartItemDto);
            return "redirect:/index";
        }
    }


    @PostMapping("/add-comment/{id}")
    public String addComment(@PathVariable Long id, @ModelAttribute("comment") CommentDto commentDto) {
        commentService.addComment(commentDto, id);
        return "redirect:/product-page/" + id;
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/my-account")
    public void myAccountPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        String role = authResult.getAuthorities().toString();

        if (role.contains("ROLE_ADMIN")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin-account"));
        } else if (role.contains("ROLE_USER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user-account"));
        }
    }


}

