package com.fasting.fasting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasting.fasting.controller.model.FastingPlan;
import com.fasting.fasting.controller.service.FastingPlanService;
import com.fasting.fasting.controller.utils.Response;

@RequestMapping("/v1/plan")
@RestController
@CrossOrigin(origins = "*")
public class FastingPlanController {
    @Autowired
    private FastingPlanService fastingPlanService;

    @PostMapping("/add")
    public ResponseEntity<Response> addNewFastingPlan(@RequestBody FastingPlan fastingPlan) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.addNewFastingPlan(fastingPlan),
                        "fasting plan successfully added!"),
                HttpStatus.OK);
    }

    @GetMapping("/{fastingPlanId}")
    public ResponseEntity<Response> getFastingPlanById(@PathVariable String fastingPlanId) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.getFastingPlanById(fastingPlanId),
                        "fetched fasting plan seccessfully!"),
                HttpStatus.OK);
    }

    @GetMapping("/startPlan/userId/{userId}/fastId/{fastId}")
    public ResponseEntity<Response> startPlanByUserIdAndPlanId(@PathVariable String userId,
            @PathVariable String fastId) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.startPlanByUserIdAndPlanId(userId, fastId),
                        "activate fasting plan seccessfully!"),
                HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<Response> getUserActivePlan(@PathVariable String userId) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.getUserActivePlan(userId),
                        "fetched fasting plan seccessfully!"),
                HttpStatus.OK);
    }
}
