package com.fasting.fasting.controller.service.helper;

import org.springframework.stereotype.Service;

import com.fasting.fasting.controller.model.Users;

@Service
public class UserHelper {
    public Users formatUserResponse(Users user){
        user.setPassword("xxxxxxxxxxxxxx");
        return user;
    }
}

