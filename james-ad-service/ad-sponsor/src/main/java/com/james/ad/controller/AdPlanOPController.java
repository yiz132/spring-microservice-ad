package com.james.ad.controller;

import com.alibaba.fastjson.JSON;
import com.james.ad.entity.AdPlan;
import com.james.ad.exception.AdException;
import com.james.ad.service.IAdPlanService;
import com.james.ad.vo.AdPlanGetRequest;
import com.james.ad.vo.AdPlanRequest;
import com.james.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class AdPlanOPController {

    private final IAdPlanService iAdPlanService;

    @Autowired
    public AdPlanOPController(IAdPlanService iAdPlanService) {
        this.iAdPlanService = iAdPlanService;
    }

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(
            @RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(request));
        return iAdPlanService.createAdPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(
            @RequestBody AdPlanGetRequest request
            ) throws AdException{
        log.info("ad-sponsor: getAdPlanByIds -> {}", JSON.toJSONString(request));
        return iAdPlanService.getAdPlanByIds(request);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor: deleteAdPlan ->{}", JSON.toJSONString(request));
        iAdPlanService.deleteAdPlan(request);
    }
}
