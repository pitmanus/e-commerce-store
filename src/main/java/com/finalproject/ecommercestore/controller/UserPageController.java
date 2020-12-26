package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.AddressDto;
import com.finalproject.ecommercestore.model.dto.ProductDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.dto.UserPaymentDto;
import com.finalproject.ecommercestore.model.entity.Product;
import com.finalproject.ecommercestore.model.entity.UserPayment;
import com.finalproject.ecommercestore.service.UserPaymentService;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UserPageController {

    private UserService userService;
    private UserPaymentService userPaymentService;


    public UserPageController(UserService userService, UserPaymentService userPaymentService) {
        this.userService = userService;
        this.userPaymentService = userPaymentService;
    }

    @GetMapping("/user-account")
    public String showUserAccountPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        UserDto userDto = userService.getUserDtoByUserName(userName);
        model.addAttribute("user", userDto);
        List<UserPaymentDto> userPaymentDtoList = userPaymentService.getAllUserPaymentCards();
        model.addAttribute("paymentCards", userPaymentDtoList);
        return "user-account";
    }

    @GetMapping("/edit-user-info/{id}")
    public String showEditUserPage(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getById(id);
        model.addAttribute("user", userDto);
        model.addAttribute("address", userDto.getAddress());
        return "edit-user-info";
    }

    @PostMapping("/edit-user")
    public String editUserInformation(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, @Valid @ModelAttribute("address") AddressDto addressDto, BindingResult bindingResult2) {
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "edit-user-info";
        } else {
            userDto.setAddress(addressDto);
            userService.fullUserUpdate(userDto);
            return "redirect:/user-account";
        }
    }

    @GetMapping("/new-payment-card")
    public String showNewPaymentCardPage(Model model) {
        UserPaymentDto userPaymentDto = new UserPaymentDto();
        model.addAttribute("paymentCard", userPaymentDto);
        return "new-payment-card";
    }

    @PostMapping("/new-payment-card")
    public String addNewPaymentCard(@ModelAttribute UserPaymentDto userPaymentDto){
        UserPayment userPayment = userPaymentService.saveUserPayment(userPaymentDto);

        MultipartFile cardImage = userPaymentDto.getCardImage();

        try {
            byte[] bytes = cardImage.getBytes();
            String name = userPayment.getId() + ".png";
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/card" + name)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/user-account";
    }

    @PostMapping("/deletecard")
    public String deleteCard(@ModelAttribute UserPaymentDto userPaymentDto){
        userPaymentService.deletePaymentCard(userPaymentDto.getId());
        return "redirect:/user-account";
    }

    @GetMapping("/edit-payment-card/{id}")
    public String showEditPaymentCardPage(@PathVariable Long id, Model model){
        UserPaymentDto userPaymentDto = userPaymentService.getUserPaymentCardById(id);
        model.addAttribute("userPaymentCard", userPaymentDto);
        return "edit-payment-card";
    }

    @PostMapping("/edit-payment-card")
    public String editPaymentCard(@ModelAttribute("userPaymentCard") UserPaymentDto userPaymentDto) {

        UserPayment userPayment = userPaymentService.saveUserPayment(userPaymentDto);

        MultipartFile cardImage = userPaymentDto.getCardImage();

        if (!cardImage.isEmpty())
            try {
                byte[] bytes = cardImage.getBytes();
                String name = userPayment.getId() + ".png";

                Files.delete(Paths.get("src/main/resources/static/image/card" + name));

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File
                                ("src/main/resources/static/image/card" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        return "redirect:/user-account";
    }

    @GetMapping("/card-details/{id}")
    public String getCardDetailsPage(@PathVariable Long id, Model model){
        UserPaymentDto userPaymentDto = userPaymentService.getUserPaymentCardById(id);
        model.addAttribute("paymentCard", userPaymentDto);
        return "card-details";
    }

}


