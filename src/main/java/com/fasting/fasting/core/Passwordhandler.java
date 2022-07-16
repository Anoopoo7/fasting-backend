package com.fasting.fasting.core;

import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class Passwordhandler {

    public String encript(String password) {
        // return String.valueOf(password.hashCode());

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            byte[] resultArray = messageDigest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i : resultArray) {
                hexString.append(Integer.toHexString(0xFF & i));
            }
            return hexString.toString();
        } catch (Exception e) {
            return password;
        }
    }

}
