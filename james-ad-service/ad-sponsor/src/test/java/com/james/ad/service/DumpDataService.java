package com.james.ad.service;

import com.james.ad.Application;
import com.james.ad.dao.AdPlanRepository;
import com.james.ad.dao.AdUnitRepository;
import com.james.ad.dao.CreativeRepository;
import com.james.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.james.ad.dao.unit_condition.AdUnitItRepository;
import com.james.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.james.ad.dao.unit_condition.CreativeUnitRepository;
import com.james.ad.entity.unit_condition.AdUnitKeyword;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUnitRepository adUnitRepository;

    @Autowired
    private CreativeRepository creativeRepository;

    @Autowired
    private CreativeUnitRepository creativeUnitRepository;

    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;

    @Autowired
    private AdUnitItRepository adUnitItRepository;

    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;
}
