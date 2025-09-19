package com.demo.session_expiry.config;

import com.demo.session_expiry.response.UserDto;

public class UserContextHolder {

    private static final ThreadLocal<UserDto> USER_CONTEXT =new ThreadLocal<>();

    private UserContextHolder() {
    }

    public static void setUserDto(UserDto userDto){
        USER_CONTEXT.set(userDto);
    }

    public static UserDto getUserDto(){
        return USER_CONTEXT.get();
    }

    public static void clear(){
        USER_CONTEXT.remove();
    }
}
