package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.CompositeStatDTO;
import com.codecool.appsystem.admin.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatisticsDataController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(method = RequestMethod.GET)
    public CompositeStatDTO stat(@RequestParam("location") String location){

        return CompositeStatDTO.builder()
                .locationId(location)
                .activeApplicationsData(statisticsService.getActiveApplicationsData(location))
                .monthlyScreeningsData(statisticsService.getMonthlyScreenings(location))
                .screeningsStatData(statisticsService.getScreeningData(location))
                .testsStatData(statisticsService.getTestsStat(location))
                .build();

    }

}
