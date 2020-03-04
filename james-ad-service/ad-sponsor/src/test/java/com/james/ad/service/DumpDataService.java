package com.james.ad.service;

import com.alibaba.fastjson.JSON;
import com.james.ad.Application;
import com.james.ad.constant.CommonStatus;
import com.james.ad.dao.AdPlanRepository;
import com.james.ad.dao.AdUnitRepository;
import com.james.ad.dao.CreativeRepository;
import com.james.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.james.ad.dao.unit_condition.AdUnitItRepository;
import com.james.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.james.ad.dao.unit_condition.CreativeUnitRepository;
import com.james.ad.dump.DConstant;
import com.james.ad.dump.table.*;
import com.james.ad.entity.AdPlan;
import com.james.ad.entity.AdUnit;
import com.james.ad.entity.Creative;
import com.james.ad.entity.unit_condition.AdUnitDistrict;
import com.james.ad.entity.unit_condition.AdUnitIt;
import com.james.ad.entity.unit_condition.AdUnitKeyword;
import com.james.ad.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    @Test
    public void dumpAdTableData(){
        dumpAdPlanTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_PLAN)
        );
        dumpAdUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT)
        );
        dumpAdCreativeTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE)
        );
        dumpAdCreativeUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_CREATIVE_UNIT)
        );
        dumpAdUnitDistrictTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_DISTRICT)
        );
        dumpAdUnitItTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_IT)
        );
        dumpAdUnitKeywordTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR,
                        DConstant.AD_UNIT_KEYWORD)
        );
    }

    private void dumpAdPlanTable(String fileName) {
        List<AdPlan> adPlans = adPlanRepository.findAllByPlanStatus(
                CommonStatus.VALID.getStatus()
        );
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }
        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(p -> planTables.add(
                new AdPlanTable(
                        p.getId(),
                        p.getUserId(),
                        p.getPlanStatus(),
                        p.getStartDate(),
                        p.getEndDate()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdPlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        }catch(IOException ex){
            log.error("dumpAdPlanTable error");
        }
    }

    private void dumpAdUnitTable(String fileName) {
        List<AdUnit> adUnits = adUnitRepository.findAllByUnitStatus(
                CommonStatus.VALID.getStatus()
        );
        List<AdUnitTable> unitTables = new ArrayList<>();
        adUnits.forEach(u -> unitTables.add(
                new AdUnitTable(
                        u.getId(),
                        u.getUnitStatus(),
                        u.getPositionType(),
                        u.getPlanId()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        }catch(IOException ex){
            log.error("dumpAdUnitTable error");
        }
    }

    private void dumpAdCreativeTable(String fileName){
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getMaterialType(),
                        c.getWidth(),
                        c.getHeight(),
                        c.getAuditStatus(),
                        c.getUrl()
                )
        ));
        Path path = Paths.get(fileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            for (AdCreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
            writer.close();
        }catch(IOException ex){
            log.error("dumpCreativeTable error");
        }
    }

    private void dumpAdCreativeUnitTable(String fileName) {

        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }

        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c -> creativeUnitTables.add(
                new AdCreativeUnitTable(
                        c.getCreativeId(),
                        c.getUnitId()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdCreativeUnit error");
        }
    }

    private void dumpAdUnitDistrictTable(String fileName) {

        List<AdUnitDistrict> unitDistricts = adUnitDistrictRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        d.getUnitId(),
                        d.getProvince(),
                        d.getCity()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdUnitDistrictTable error");
        }
    }

    private void dumpAdUnitItTable(String fileName) {

        List<AdUnitIt> unitIts = adUnitItRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }

        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(i -> unitItTables.add(
                new AdUnitItTable(
                        i.getUnitId(),
                        i.getItTag()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdUnitItTable error");
        }
    }

    private void dumpAdUnitKeywordTable(String fileName) {

        List<AdUnitKeyword> unitKeywords = adUnitKeywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(k -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        k.getUnitId(),
                        k.getKeyword()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            log.error("dumpAdUnitItTable error");
        }
    }
}
