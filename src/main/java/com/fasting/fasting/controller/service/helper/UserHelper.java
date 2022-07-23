package com.fasting.fasting.controller.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasting.fasting.controller.model.Users;
import com.fasting.fasting.controller.service.ProfilePhotosServices;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserHelper {
    @Autowired
    ProfilePhotosServices profilePhotosServices;

    public Users formatUserResponse(Users user) {
        try {
            user.setImage(profilePhotosServices.getUserPhoto(user.getId()));
        } catch (Exception e) {
            log.info("no image available for the user mail: {}", user.getEmail());
            user.setImage(null);
        }
        user.setPassword("xxxxxxxxxxxxxx");
        return user;
    }
}
