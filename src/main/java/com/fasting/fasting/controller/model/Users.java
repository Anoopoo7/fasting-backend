package com.fasting.fasting.controller.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "fas_users")
public class Users {
    @Id
    private String id;
    private String first_name;
    private String last_name;
    private int age;
    private String bio;
    private String image;
    private String email;
    private String password;
    private Map<String,Boolean> fastIds;
    private boolean active;
    private Date lastUpdated;
}
