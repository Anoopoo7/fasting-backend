package com.fasting.fasting.controller.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fasting.fasting.controller.model.Users;


@Repository
public interface UserRepository extends MongoRepository <Users,String> {

    Users findByEmail(String email);

    Users findByEmailAndPassword(String email, String encript);
    
}
