package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.UserDTO;
import com.codecool.appsystem.admin.service.UsersByLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DashboardRestController {

    @Autowired
    private UsersByLocationService userLocationService;


    @RequestMapping(value = "/api/dashboard/motivation", method = RequestMethod.GET, params = {"locationId"})
    public @ResponseBody List<UserDTO> applicantsByLocation(@RequestParam String locationId) {

        return userLocationService.applicantsByLocation(locationId);
    }


}
