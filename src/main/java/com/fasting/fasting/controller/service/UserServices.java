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
    @Autowired
    private ProfilePhotosServices profilePhotosServices;

    public Object getUserRegister(Users userdata) {
        if (null == userdata) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_DATA_IS_EMPTY.name());
        }
        Users user = getUserByemail(userdata.getEmail());
        if (user != null) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_ALREADY_EXISTS.name());
        }
        userdata.setPassword(passwordhandler.encript(userdata.getPassword()));
        userdata.setLastUpdated(new Date());
        userdata.setActive(true);
        userRepository.save(userdata);
        log.info("saving new user with details {}", userdata);
        return userHelper.formatUserResponse(userdata);
    }

    public Users getUserByemail(String email) {
        Users user = userRepository.findByEmail(email);
        log.info("user details fetches as {} form mail: {}", user, email);
        return user;

    }

    public Users getUserByUserId(String userId) {
        Users user = null;
        try {
            user = userRepository.findById(userId).get();
            log.info("user details fetches as {} form Id: {}", user, userId);
        } catch (Exception e) {
            log.info("user details fetches as {} form Id: {}", user, userId);
        }
        return user;
    }

    public Object getUserLoggedIn(Users userdata) {
        log.info("user details received as {}", userdata);
        if (null == userdata) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_DATA_IS_EMPTY.name());
        }
        Users user = userRepository.findByEmailAndPassword(userdata.getEmail(),
                passwordhandler.encript(userdata.getPassword()));
        if (null == user) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_NOT_FOUND.name());
        }
        user.setLastUpdated(new Date());
        userRepository.save(user);
        return userHelper.formatUserResponse(user);
    }

    public Object editUserData(Users userdata) {
        if (null == userdata) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_DATA_IS_EMPTY.name());
        }
        Users user = getUserByemail(userdata.getEmail());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_NOT_FOUND.name());
        }
        user.setFirst_name(userdata.getFirst_name());
        user.setBio(userdata.getBio());
        log.info("saving new user with details {}", user);
        userRepository.save(user);
        return userHelper.formatUserResponse(user);
    }

    public Object uploadPhoto(Users userdata) {
        if (null == userdata) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_DATA_IS_EMPTY.name());
        }
        Users user = getUserByemail(userdata.getEmail());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_NOT_FOUND.name());
        }
        profilePhotosServices.savePhotoToUser(user.getId(),userdata.getImage());
        user.setImage(user.getId());
        log.info("saving new user with details {}", user.getEmail());
        userRepository.save(user);
        return userHelper.formatUserResponse(user);
    }
}
