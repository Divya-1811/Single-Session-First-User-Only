package com.demo.session_expiry.controller;

import com.demo.session_expiry.dto.LoginDto;
import com.demo.session_expiry.dto.UserDto;
import com.demo.session_expiry.model.User;
import com.demo.session_expiry.response.ApiResponse;
import com.demo.session_expiry.response.AuthResponse;
import com.demo.session_expiry.service.UserService;
import com.demo.session_expiry.serviceimpl.AuthService;
import com.demo.session_expiry.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return CommonUtil.getOkResponse("User save successfully","");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(@RequestBody LoginDto loginDto){
        User user=userService.userLogin(loginDto);
        AuthResponse authResponse=authService.generateToken(user);
        return CommonUtil.getOkResponse("User login successfully",authResponse);
    }

    @GetMapping("/login/get/{userId}")
    public ResponseEntity<ApiResponse> getByUserId(@PathVariable("userId") Long userId){
        User user=userService.getByUserId(userId);
        return CommonUtil.getOkResponse("Success",user);
    }

    @GetMapping("/login/get")
    public ResponseEntity<ApiResponse> getByRole(){
        User user=userService.getByRole();
        return CommonUtil.getOkResponse("Success",user);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return CommonUtil.getOkResponse("Logout Successfully", "");
    }

}
