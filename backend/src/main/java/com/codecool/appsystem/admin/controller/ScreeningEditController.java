package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.service.ScreeningEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningEditController {

    @Autowired
    private ScreeningEditService screeningEditService;

    @RequestMapping(value = "/api/editscrening", method = RequestMethod.GET)
    public List<ScreeningStep> findByLocation(@RequestParam("locationId") String locationId){
        return screeningEditService.findByLocationId(locationId);
    }

    @RequestMapping(value = "/api/editscrening", method = RequestMethod.POST)
    public RestResponseDTO saveScreening(@RequestBody List<ScreeningStep> data){
        screeningEditService.saveScreening(data);
        return RestResponseDTO.buildSuccess();
    }

}
