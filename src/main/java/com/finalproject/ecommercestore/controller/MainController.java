package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.*;
import com.finalproject.ecommercestore.service.*;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        return findPaginated(model, 1);
    }

    @GetMapping("/page/{pageNum}")
    public String findPaginated(Model model, @PathVariable int pageNum) {
        int pageSize = 6;
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<ProductDto> list = productService.getAllProducts();
        PagedListHolder<ProductDto> page = new PagedListHolder<>(list);
        page.setPageSize(pageSize);
        page.setPage(pageNum-1);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getPageCount());
        model.addAttribute("totalItems", list.size());
        model.addAttribute("productList", page.getPageList());

        return "index";
    }


    @ModelAttribute("user")
    public UserDto userInViews(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<? extends GrantedAuthority> optAuthority = auth.getAuthorities().stream().findFirst();
        GrantedAuthority authority = null;
        if (!optAuthority.isEmpty())
             authority = optAuthority.get();
        if (auth.getPrincipal().equals("anonymousUser")||authority.getAuthority().equals("ROLE_ADMIN")){
            return null;
        }
        return userService.getLoggedUser();
    }

    @GetMapping("/products/{id}")
    public String showFilteredByCategoryProducts(@PathVariable Long id, Model model) {
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<ProductDto> productList = productService.getProductsByCategories(id);
        model.addAttribute("productList", productList);
        return "filtered-products";
    }

    @RequestMapping(value = "search-products", method = RequestMethod.GET)
    public String showFilteredByProductName(@RequestParam(value = "productName", required = false, defaultValue = "") String productName, Model model) {
        List<CategoryDto> categories = categoryService.showAllCategories();
        model.addAttribute("categories", categories);
        List<ProductDto> productList = productService.getProductsByProductName(productName);
        model.addAttribute("productList", productList);
        return "filtered-products";
    }

    @GetMapping("/product-page/{id}")
    public String showSingleProductPage(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority authority = auth.getAuthorities().stream().findFirst().get();
        if (authority.getAuthority().equals("ROLE_ADMIN")){
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
        }else {
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

    }

    @PostMapping("/add-to-cart/{id}")
    public String addToCart( @Valid @ModelAttribute("cartItem") CartItemDto cartItemDto, BindingResult bindingResult, @PathVariable Long id, Model model) {
        ProductDto productDto = productService.getById(id);
        if (bindingResult.hasErrors()){
            List<CategoryDto> categories = categoryService.showAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("product", productDto);
            CommentDto commentDto = new CommentDto();
            model.addAttribute("comment", commentDto);
            List<CommentDto> comments = commentService.getAllCommentsForASingleProduct(id);
            model.addAttribute("comments", comments);
            return "product-page";
        }else{
            shoppingCartService.addItemToShoppingCart(id, cartItemDto);
            return "redirect:/index?success";
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

