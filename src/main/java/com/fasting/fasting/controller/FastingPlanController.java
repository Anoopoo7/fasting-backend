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
import com.fasting.fasting.controller.model.StartPlanRequestView;
import com.fasting.fasting.controller.service.FastingPlanService;
import com.fasting.fasting.controller.utils.Response;

@RequestMapping("/v1/plan")
@RestController
@CrossOrigin(origins = "*")
public class FastingPlanController {
    @Autowired
    private FastingPlanService fastingPlanService;

    @GetMapping("page/{page}")
    public ResponseEntity<Response> getPlans(@PathVariable int page) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.getPlans(page),
                        "fetched fasting plan seccessfully!"),
                HttpStatus.OK);
    }

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

    @PostMapping("/startPlan")
    public ResponseEntity<Response> startPlanByUserIdAndPlanId(@RequestBody StartPlanRequestView startPlanRequestView) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.startPlanByUserIdAndPlanId(startPlanRequestView),
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

    @GetMapping("/all/userId/{userId}")
    public ResponseEntity<Response> getAllUserPlans(@PathVariable String userId) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.getAllUserPlans(userId),
                        "fetched fasting plans seccessfully!"),
                HttpStatus.OK);
    }

    @PostMapping("/update/userId/{userId}")
    public ResponseEntity<Response> updateFastingItemStatusById(@PathVariable String userId,
            @RequestBody FastingPlan UpdateFastingItem) {
        return new ResponseEntity<>(
                new Response(true, fastingPlanService.updateFastingItemStatusById(userId, UpdateFastingItem),
                        "Updated fasting plan seccessfully!"),
                HttpStatus.OK);
    }
}
