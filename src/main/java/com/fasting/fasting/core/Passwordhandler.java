package com.fasting.fasting.core;

import org.springframework.stereotype.Service;

@Service
public class Passwordhandler {

    public String encript(String password) {
        return String.valueOf(password.hashCode());
    }

}
