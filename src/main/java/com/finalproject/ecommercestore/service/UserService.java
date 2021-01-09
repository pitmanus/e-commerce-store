package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.entity.*;
import com.finalproject.ecommercestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public User saveUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Arrays.asList(new Role(RoleNames.ROLE_USER)));
        user.setShoppingCart(new ShoppingCart());
        return userRepository.save(user);
    }

    public void fullUserUpdate(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public User updateUser(UserDto userDto) {
        return userRepository.save(modelMapper.map(userDto, User.class));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .findAny()
                .orElse(null);
    }


    public UserDto getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getUserDtoByUserName(auth.getName());
    }

    public UserDto getUserDtoByUserName(String userName) {
        return modelMapper.map(userRepository.findByUsername(userName), UserDto.class);
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }
}
