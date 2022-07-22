package com.fasting.fasting.controller.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fasting.fasting.controller.model.ProfilePhotos;

@Repository
public interface ProfilePhotosRepository extends MongoRepository<ProfilePhotos, String> {

    String findByUserIdAndActive(String userId, boolean b);

    ProfilePhotos findByUserId(String id);

}
