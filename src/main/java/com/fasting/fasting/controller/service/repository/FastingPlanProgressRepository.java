package com.fasting.fasting.controller.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fasting.fasting.controller.model.FastingPlanProgress;

@Repository
public interface FastingPlanProgressRepository extends MongoRepository<FastingPlanProgress, String> {

    FastingPlanProgress findByUserIdAndStatus(String userId, boolean b);

    FastingPlanProgress findByUserIdAndStatusAndActiveDaysIn(String userId, boolean b, List<String> todayList);

    FastingPlanProgress findByUserIdAndStatusAndEnabledAndActiveDaysIn(String userId, boolean b, boolean c,
            List<String> todayList);

}
