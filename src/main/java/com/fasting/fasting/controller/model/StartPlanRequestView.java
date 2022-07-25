package com.fasting.fasting.controller.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class StartPlanRequestView {
    private String userId;
    private String fastId;
    private String startDay;
}
