package com.fasting.fasting.controller.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fasting_item {
    private String data;
    private String time;
    private String status;
    private Date lastUpdate;
}
