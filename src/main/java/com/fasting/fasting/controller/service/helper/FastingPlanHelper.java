package com.fasting.fasting.controller.service.helper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasting.fasting.controller.model.FastingPlan;
import com.fasting.fasting.controller.model.FastingPlanResponse;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FastingPlanHelper {

    public Object formatFastingPageList(List<FastingPlan> fastingPlan, Long totalCount) {
        log.info("recived data {} and total count {}", fastingPlan, totalCount);
        FastingPlanResponse fastingPlanResponse = new FastingPlanResponse();
        fastingPlanResponse.setFastingPlan(fastingPlan);
        fastingPlanResponse.setTotalCounts(totalCount);
        long partial = totalCount % 10;
        long full = (totalCount - partial) / 10;
        fastingPlanResponse.setPages((full + (partial != 0 ? 1 : 0)) - 1);
        return fastingPlanResponse;
    }

}
