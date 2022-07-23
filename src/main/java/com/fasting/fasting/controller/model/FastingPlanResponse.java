package com.fasting.fasting.controller.model;

import java.util.List;

import lombok.Data;
@Data
public class FastingPlanResponse {
    private List<FastingPlan> fastingPlan;
    private long totalCounts;
    private long pages;
}
