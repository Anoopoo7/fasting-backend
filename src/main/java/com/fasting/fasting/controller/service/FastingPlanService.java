package com.fasting.fasting.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasting.fasting.controller.model.FastingPlan;
import com.fasting.fasting.controller.service.repository.FastingPlanRepository;
import com.fasting.fasting.core.FasException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FastingPlanService {
    @Autowired
    private FastingPlanRepository fastingPlanRepository;

    public Object addNewFastingPlan(FastingPlan fastingPlan) {
        if (fastingPlan.getFasting_items().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_FASTING_PLAN.name());
        }
        fastingPlan.setDeficultyRate(0);
        fastingPlan.setSuccessRate(0);
        fastingPlan.setTotalUsers(0);
        log.info("saving fasting plan {}", fastingPlan);
        fastingPlanRepository.save(fastingPlan);
        return fastingPlan;
    }

    public Object getFastingPlanById(String fastingPlanId) {
        if (null == fastingPlanId) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_DATA.name());
        }
        try {
            FastingPlan fastingPlan = fastingPlanRepository.findById(fastingPlanId).get();
            log.info("fetched fasting plan {}", fastingPlan);
            return fastingPlan;
        } catch (Exception e) {
            log.info("no plan is available with {} id", fastingPlanId);
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_DATA.name());
        }
    }
}
