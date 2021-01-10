package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.UserPaymentDto;
import com.finalproject.ecommercestore.model.entity.User;
import com.finalproject.ecommercestore.model.entity.UserPayment;
import com.finalproject.ecommercestore.repository.UserPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
@Service
public class UserPaymentService {
    private UserPaymentRepository userPaymentRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    public UserPaymentService(UserPaymentRepository userPaymentRepository, ModelMapper modelMapper, UserService userService) {
        this.userPaymentRepository = userPaymentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public String getUserNameFromSecurityContext(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public UserPayment saveUserPayment(UserPaymentDto userPaymentDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.getUserByUserName(name);
        UserPayment userPayment = modelMapper.map(userPaymentDto, UserPayment.class);
        userPayment.setBillingAddress(user.getAddress());
        user.getUserPayments().add(userPayment);
        userService.save(user);
        return user.getUserPayments().stream()
                .filter(payment -> payment.getCardNumber().equals(userPaymentDto.getCardNumber()))
                .findFirst().get();
    }

    public void addUserPayment(UserPaymentDto userPaymentDto){
        UserPayment userPayment = saveUserPayment(userPaymentDto);

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
    }

    public void editUserPayment(UserPaymentDto userPaymentDto){
        UserPayment userPayment = saveUserPayment(userPaymentDto);

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
    }

    public List<UserPaymentDto> getAllUserPaymentCards(){
        return userService.getUserDtoByUserName(getUserNameFromSecurityContext()).getUserPayments();
    }

    public UserPaymentDto getUserPaymentCardById(Long id){
       return modelMapper.map(userPaymentRepository.findById(id).get(), UserPaymentDto.class);
    }

    public void deletePaymentCard(Long id){
        userPaymentRepository.deleteById(id);
    }

}
