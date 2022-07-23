package com.fasting.fasting.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasting.fasting.controller.model.ProfilePhotos;
import com.fasting.fasting.controller.service.repository.ProfilePhotosRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProfilePhotosServices {

    @Autowired
    private ProfilePhotosRepository profilePhotosRepository;

    public void savePhotoToUser(String id, String image) {
        ProfilePhotos existingPhoto = profilePhotosRepository.findByUserId(id);
        if (null != existingPhoto) {
            existingPhoto.setPhoto(image);
            profilePhotosRepository.save(existingPhoto);
            log.info("saving new photo to user with details {}", existingPhoto);
            return;
        }
        ProfilePhotos profilePhoto = new ProfilePhotos();
        profilePhoto.setPhoto(image);
        profilePhoto.setUserId(id);
        profilePhoto.setActive(true);
        log.info("saving new photo to user with details {}", profilePhoto);
        profilePhotosRepository.save(profilePhoto);
    }

    public String getUserPhoto(String userId) {
        ProfilePhotos profilePhotos = profilePhotosRepository.findByUserIdAndActive(userId, true);
        return (null != profilePhotos) ? profilePhotos.getPhoto() : null;
    }

}
