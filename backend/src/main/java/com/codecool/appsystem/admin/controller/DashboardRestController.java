package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.service.ApplicationScreeningService;
import com.codecool.appsystem.admin.service.MotivationsByLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardRestController {

    @Autowired
    private MotivationsByLocationService userMotivationService;

    @Autowired
    private ApplicationScreeningService screeningService;

    @RequestMapping(value = "/motivation", method = RequestMethod.GET)
    public List<MotivationDTO> applicantsByLocation(@RequestParam("location") String locationId) {
        return userMotivationService.applicantsByLocation(locationId);
    }

    @RequestMapping(value = "/screening", method = RequestMethod.GET)
    public List<ScreeningDTO> scheduledScreenings(@RequestParam("location") String locationId) throws Exception{
        return screeningService.find(locationId, null);
    }

}
