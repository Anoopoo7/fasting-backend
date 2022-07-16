package com.fasting.fasting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasting.fasting.controller.model.Users;
import com.fasting.fasting.controller.service.UserServices;
import com.fasting.fasting.controller.utils.Response;

@RequestMapping("/v1/user")
@RestController
@CrossOrigin(origins = "*")
public class Usercontroller {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<Response> getUserRegister(@RequestBody Users userdata) {
        return new ResponseEntity<>(
                new Response(true, userServices.getUserRegister(userdata), "user successfully registed!"),
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> getUserLoggedIn(@RequestBody Users userdata) {
        return new ResponseEntity<>(
                new Response(true, userServices.getUserLoggedIn(userdata), "user successfully loggedin!"),
                HttpStatus.OK);
    }

}
    