package com.demo.session_expiry.service;

import com.demo.session_expiry.dto.LoginDto;
import com.demo.session_expiry.dto.UserDto;
import com.demo.session_expiry.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void saveUser(UserDto userDto);

    User userLogin(LoginDto loginDto);

    User getByUserId(Long id);

    User getByRole();
}
