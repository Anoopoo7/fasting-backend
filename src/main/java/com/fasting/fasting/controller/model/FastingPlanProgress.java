package com.fasting.fasting.controller.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "fas_fasting_plan_progress")
public class FastingPlanProgress {
    @Id
    private String id;
    private String userId;
    private FastingPlan fastingPlan;
    private boolean status;
    private Date updatedDate;
    private Date startDate;
    private List<String> activeDays;
    private boolean enabled;
}
