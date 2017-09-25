package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.service.ScreeningEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningEditController {

    @Autowired
    private ScreeningEditService screeningEditService;

    @RequestMapping(value = "/api/editscreening", method = RequestMethod.GET)
    public List<ScreeningStep> findByLocation(@RequestParam("location") String locationId){
        return screeningEditService.findByLocationId(locationId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/api/editscreening", method = RequestMethod.POST)
    public void saveScreening(@RequestBody List<ScreeningStep> data){
        screeningEditService.saveScreening(data);
    }

}
