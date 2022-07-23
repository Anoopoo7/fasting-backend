package com.fasting.fasting.controller.service.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.fasting.fasting.controller.model.FastingPlan;

@Repository
public class FastingPlanCustomRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<FastingPlan> getTopFastingPlanWithPagination(int page) {
        final Pageable pageableRequest = PageRequest.of(page, 10);
        Query query = new Query();
        query.addCriteria(Criteria.where("active").is(true));
        query.with(Sort.by(Sort.Direction.DESC, "createdDate"));
        query.with(pageableRequest);
        try {
            return mongoTemplate.find(query, FastingPlan.class);
        } catch (Exception e) {
            return null;
        }
    }
}
