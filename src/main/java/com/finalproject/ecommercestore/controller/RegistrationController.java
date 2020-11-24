package com.finalproject.ecommercestore.controller;

import com.finalproject.ecommercestore.model.dto.AddressDto;
import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new UserDto());
        model.addAttribute("address", new AddressDto());
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDto userDto, @ModelAttribute("address")AddressDto addressDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println("BINDING RESULT ERROR");
            return "registration";
        }else{
            userDto.setAddress(addressDto);
            userService.saveUser(userDto);
            return "redirect:/registration?success";
        }
    }


}
