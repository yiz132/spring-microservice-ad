package com.james.ad.service;

import com.james.ad.entity.AdPlan;
import com.james.ad.exception.AdException;
import com.james.ad.vo.AdPlanGetRequest;
import com.james.ad.vo.AdPlanRequest;
import com.james.ad.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {

    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
