package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.UserDTO;
import com.codecool.appsystem.admin.service.ApplicationScreeningService;
import com.codecool.appsystem.admin.service.UsersByLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardRestController {

    @Autowired
    private UsersByLocationService userLocationService;

    @Autowired
    private ApplicationScreeningService screeningService;

    @RequestMapping(value = "/motivation", method = RequestMethod.GET)
    public List<UserDTO> applicantsByLocation(@RequestParam("location") String locationId) {
        return userLocationService.applicantsByLocation(locationId);
    }

    @RequestMapping(value = "/screening", method = RequestMethod.GET)
    public List<ScreeningDTO> scheduledScreenings(@RequestParam("location") String locationId) throws Exception{
        return screeningService.find(locationId);
    }

}
