package com.demo.session_expiry.serviceimpl;

import com.demo.session_expiry.config.UserContextHolder;
import com.demo.session_expiry.dto.LoginDto;
import com.demo.session_expiry.dto.UserDto;
import com.demo.session_expiry.exception.CustomException;
import com.demo.session_expiry.model.User;
import com.demo.session_expiry.repository.UserRepository;
import com.demo.session_expiry.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        Boolean existEmail=userRepository.existsByEmailIgnoreCase(userDto.getEmail());
        if (Boolean.TRUE.equals(existEmail)){
            throw new CustomException("Email already exists");
        }
        User user=new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }

    @Override
    public User userLogin(LoginDto loginDto) {
        User user=userRepository.findByEmail(loginDto.getEmail()).
                orElseThrow(()->new CustomException("Invalid email"));
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new CustomException("Invalid password");
        }
        return user;
    }

    @Override
    public User getByUserId(Long id) {
        return userRepository.findById(id).
                orElseThrow(()->new CustomException("User not found"));
    }

    @Override
    public User getByRole() {
        Long id= UserContextHolder.getUserDto().getId();
        return userRepository.findById(id).
                orElseThrow(()->new CustomException("User not found"));
    }
}
