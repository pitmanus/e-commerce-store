package com.finalproject.ecommercestore.service;

import com.finalproject.ecommercestore.model.dto.UserDto;
import com.finalproject.ecommercestore.model.entity.Address;
import com.finalproject.ecommercestore.model.entity.Role;
import com.finalproject.ecommercestore.model.entity.User;
import com.finalproject.ecommercestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    public User updateUser(UserDto userDto){
        return userRepository.save(modelMapper.map(userDto, User.class));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getById(Long id){
        return userRepository.findById(id)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .findAny()
                .orElse(null);
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
