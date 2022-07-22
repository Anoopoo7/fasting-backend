package com.fasting.fasting.controller.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasting.fasting.controller.model.FastingPlan;
import com.fasting.fasting.controller.model.FastingPlanProgress;
import com.fasting.fasting.controller.model.Fasting_item;
import com.fasting.fasting.controller.model.Users;
import com.fasting.fasting.controller.service.repository.FastingPlanProgressRepository;
import com.fasting.fasting.controller.service.repository.FastingPlanRepository;
import com.fasting.fasting.core.FasException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FastingPlanService {
    @Autowired
    private FastingPlanRepository fastingPlanRepository;
    @Autowired
    private FastingPlanProgressRepository fastingPlanProgressRepository;
    @Autowired
    private UserServices userServices;

    public Object addNewFastingPlan(FastingPlan fastingPlan) {
        if (fastingPlan.getFasting_items().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_FASTING_PLAN.name());
        }
        fastingPlan.setDeficultyRate(0);
        fastingPlan.setSuccessRate(0);
        fastingPlan.setTotalUsers(0);
        fastingPlan.setActive(true);
        fastingPlan.setCreatedDate(new Date());
        log.info("saving fasting plan {}", fastingPlan);
        fastingPlanRepository.save(fastingPlan);
        return fastingPlan;
    }

    public FastingPlan getFastingPlanById(String fastingPlanId) {
        if (null == fastingPlanId) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_DATA.name());
        }
        try {
            FastingPlan fastingPlan = fastingPlanRepository.findByIdAndActive(fastingPlanId, true);
            log.info("fetched fasting plan {}", fastingPlan);
            return fastingPlan;
        } catch (Exception e) {
            log.info("no plan is available with {} id", fastingPlanId);
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_DATA.name());
        }
    }

    public Object startPlanByUserIdAndPlanId(String userId, String fastId) {
        log.info("fetched fasting plan {} and userId {}", fastId, userId);
        if (userId == null || fastId == null) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_DATA.name());
        }
        if(getUserActivePlan(userId) != null){
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_ALREADY_IN_A_PLAN.name());
        }
        Users user = userServices.getUserByUserId(userId);
        FastingPlan fastingPlan = this.getFastingPlanById(fastId);
        if (user == null || fastingPlan == null) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_DATA.name());
        }
        FastingPlanProgress fastingPlanProgress = new FastingPlanProgress();
        fastingPlanProgress.setUserId(user.getId());
        fastingPlanProgress.setFastingPlan(fastingPlan);
        List<Fasting_item> fasting_items = fastingPlan.getFasting_items();
        for (Fasting_item fasting_item : fasting_items) {
            fasting_item.setStatus("PENDING");
            fasting_item.setLastUpdate(new Date());
        }
        LocalDate date = LocalDate.now();
        fastingPlanProgress.setStartDate(java.sql.Date.valueOf(date.plusDays(1)));
        fastingPlanProgress.getFastingPlan().setFasting_items(fasting_items);
        fastingPlanProgress.setUpdatedDate(new Date());
        log.info("adding fasting plan {} with userId {} is {} ", fastId, userId, fastingPlanProgress);
        fastingPlanProgressRepository.save(fastingPlanProgress);
        return fastingPlanProgress;
    }

    public Object getUserActivePlan(String userId) {
        log.info("reached userId {}", userId);
        FastingPlanProgress userCurrentPlan = fastingPlanProgressRepository.findByUserIdAndStatus(userId, false);
        log.info("fetched fastplan {}", userCurrentPlan);
        return userCurrentPlan;
    }
}
