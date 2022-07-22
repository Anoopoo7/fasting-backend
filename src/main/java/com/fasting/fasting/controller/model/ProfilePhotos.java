package com.fasting.fasting.controller.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "fas_profile_photos")
public class ProfilePhotos {
    private String userId;
    private boolean active;
    private String photo;
}
