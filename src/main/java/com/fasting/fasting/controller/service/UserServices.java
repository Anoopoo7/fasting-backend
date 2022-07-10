package com.fasting.fasting.controller.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasting.fasting.controller.model.Users;
import com.fasting.fasting.controller.service.helper.UserHelper;
import com.fasting.fasting.controller.service.repository.UserRepository;
import com.fasting.fasting.core.FasException;
import com.fasting.fasting.core.Passwordhandler;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Passwordhandler passwordhandler;
    @Autowired
    private UserHelper userHelper;

    public Object getUserRegister(Users userdata) {
        if (null == userdata) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, FasException.USER_DATA_IS_EMPTY.name());
        }
        Users user = getUserByemail(userdata.getEmail());
        if (user != null) {
            throw new ResponseStatusException(
                    HttpStatus.ALREADY_REPORTED, FasException.USER_ALREADY_EXISTS.name());
        }
        userdata.setPassword(passwordhandler.encript(userdata.getPassword()));
        userdata.setLastUpdated(new Date());
        userdata.setActive(true);
        userRepository.save(userdata);
        log.info("saving new user with details {}", userdata);
        return userHelper.formatUserResponse(userdata);
    }

    private Users getUserByemail(String email) {
        Users user = userRepository.findByEmail(email);
        log.info("user details fetches as {} form mail: {}", user, email);
        return user;

    }

    public Object getUserLoggedIn(Users userdata) {
        if (null == userdata) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, FasException.USER_DATA_IS_EMPTY.name());
        }
        Users user = userRepository.findByEmailAndPassword(userdata.getEmail(),
                passwordhandler.encript(userdata.getPassword()));
        if (null == user) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, FasException.USER_NOT_FOUND.name());
        }
        user.setLastUpdated(new Date());
        userRepository.save(user);
        return userHelper.formatUserResponse(user);
    }
}
