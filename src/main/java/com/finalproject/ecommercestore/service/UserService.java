package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.entity.Address;
import com.finalproject.ecommercestore.model.entity.Role;
import com.finalproject.ecommercestore.model.entity.User;
import com.finalproject.ecommercestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {

    private BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(UserDto userDto) {
        User user = new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getUsername(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getTel(),
                new Address(
                        userDto.getAddress().getStreet(),
                        userDto.getAddress().getBuildingNumberAndApartment(),
                        userDto.getAddress().getCity(),
                        userDto.getAddress().getZip()),
                Arrays.asList(new Role("USER"))
        );
        return userRepository.save(user);
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
