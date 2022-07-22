package com.fasting.fasting.controller.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fasting.fasting.controller.model.FastingPlanProgress;

@Repository
public interface FastingPlanProgressRepository extends MongoRepository<FastingPlanProgress, String> {

    FastingPlanProgress findByUserIdAndStatus(String userId, boolean b);

}
