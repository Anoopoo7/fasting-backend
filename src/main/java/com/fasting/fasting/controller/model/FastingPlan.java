package com.fasting.fasting.controller.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "fas_fasting_plan")
public class FastingPlan {
    @Id
    private String id;
    private String name;
    private String foodType;
    private String ageGroup;
    private Integer duration;
    private List<Fasting_item> fasting_items;
    private List<String> labels;
    private boolean visible;
    private boolean accesseble;
    private Integer deficultyRate;
    private Integer successRate;
    private Integer totalUsers;
    private List<String> category;
    private boolean active;
    private String createdBy;
    private Date createdDate;
}
