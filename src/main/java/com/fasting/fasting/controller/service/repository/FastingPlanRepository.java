package com.fasting.fasting.controller.service.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.fasting.fasting.controller.model.FastingPlan;

@Repository
public interface FastingPlanRepository extends MongoRepository<FastingPlan, String> {

    FastingPlan findByIdAndActive(String fastingPlanId, boolean active);

}
