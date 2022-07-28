package com.fasting.fasting.controller.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasting.fasting.controller.model.FastingPlan;
import com.fasting.fasting.controller.model.FastingPlanProgress;
import com.fasting.fasting.controller.model.Fasting_item;
import com.fasting.fasting.controller.model.StartPlanRequestView;
import com.fasting.fasting.controller.model.Users;
import com.fasting.fasting.controller.service.helper.FastingPlanHelper;
import com.fasting.fasting.controller.service.repository.FastingPlanCustomRepository;
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
    @Autowired
    private FastingPlanCustomRepository fastingPlanCustomRepository;
    @Autowired
    private FastingPlanHelper fastingPlanHelper;

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

    public FastingPlanProgress getFastPlanByUserId(String userId) {
        return fastingPlanProgressRepository.findByUserIdAndEnabled(userId, true);
    }

    public Object startPlanByUserIdAndPlanId(StartPlanRequestView startPlanRequestView) {
        log.info("fetched fasting plan {}", startPlanRequestView);
        if (null == startPlanRequestView) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.INVALID_DATA.name());
        }
        if (getFastPlanByUserId(startPlanRequestView.getUserId()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.OK, FasException.USER_ALREADY_IN_A_PLAN.name());
        }
        Users user = userServices.getUserByUserId(startPlanRequestView.getUserId());
        FastingPlan fastingPlan = this.getFastingPlanById(startPlanRequestView.getFastId());
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
        switch (startPlanRequestView.getStartDay()) {
            case "TOMARROW":
                fastingPlanProgress.setStartDate(java.sql.Date.valueOf(date.plusDays(1)));
                break;
            case "DAYAFTERTOMARROW":
                fastingPlanProgress.setStartDate(java.sql.Date.valueOf(date.plusDays(2)));
                break;
            default:
                fastingPlanProgress.setStartDate(java.sql.Date.valueOf(date.plusDays(0)));
                break;
        }
        Date startDate = fastingPlanProgress.getStartDate();
        List<String> activeDays = new ArrayList<String>();
        String s_day = startDate.getYear() + "-" + startDate.getMonth() + "-" + startDate.getDate();
        activeDays.add(s_day);
        for (int i = 1; i < fastingPlan.getDuration(); i++) {
            if (i == 0) {
                Date day_day = new Date(startDate.getTime() + (1000 * 60 * 60 * 24));
                String day = day_day.getYear() + "-" + day_day.getMonth() + "-" + day_day.getDate();
                activeDays.add(day);
            } else {
                Date day_day = new Date(startDate.getTime() + (i * 1000 * 60 * 60 * 24));
                String day = day_day.getYear() + "-" + day_day.getMonth() + "-" + day_day.getDate();
                activeDays.add(day);
            }
        }
        fastingPlanProgress.setActiveDays(activeDays);
        fastingPlanProgress.getFastingPlan().setFasting_items(fasting_items);
        fastingPlanProgress.setUpdatedDate(new Date());
        fastingPlanProgress.setEnabled(true);
        log.info("adding fasting plan {} with userId {} is {} ", startPlanRequestView.getFastId(),
                startPlanRequestView.getUserId(), fastingPlanProgress);
        fastingPlanProgressRepository.save(fastingPlanProgress);
        return fastingPlanProgress;
    }

    public Object getUserActivePlan(String userId) {
        log.info("reached userId {}", userId);
        Date today_day = new Date();
        String today = today_day.getYear() + "-" + today_day.getMonth() + "-" + today_day.getDate();
        List<String> todayList = new ArrayList<>();
        todayList.add(today);
        FastingPlanProgress userCurrentPlan = fastingPlanProgressRepository
                .findByUserIdAndStatusAndEnabledAndActiveDaysIn(userId,
                        false, true, todayList);
        log.info("fetched fastplan {}", userCurrentPlan);
        return userCurrentPlan;
    }

    public Object getPlans(int page) {
        List<FastingPlan> fastingPlan = fastingPlanCustomRepository.getTopFastingPlanWithPagination(page);
        Long totalCount = fastingPlanRepository.countByActive(true);
        return fastingPlanHelper.formatFastingPageList(fastingPlan, totalCount, page);
    }
}
