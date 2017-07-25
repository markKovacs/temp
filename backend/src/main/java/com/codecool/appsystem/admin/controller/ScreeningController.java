package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningTimeAssingmentDTO;
import com.codecool.appsystem.admin.service.ApplicationScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningController {

    @Autowired
    private ApplicationScreeningService screeningService;

    @RequestMapping("/api/screening/list")
    public List<ScreeningDTO> getScreeningInfo(@RequestParam String location, @RequestParam("signedback") Boolean signedBack){
        return screeningService.find(location, signedBack);
    }

    @RequestMapping("/api/screening/candidates")
    public List<ScreeningDTO> getScreeningCandidates(@RequestParam String location){
        return screeningService.getCandidates(location);
    }

    @RequestMapping(value = "/api/screening/group", method = RequestMethod.POST)
    public RestResponseDTO saveGroupTimes(@RequestBody List<ScreeningTimeAssingmentDTO> data){
        screeningService.saveGroupScreeningTime(data);
        return RestResponseDTO.buildSuccess();
    }

    @RequestMapping(value = "/api/screening/personal", method = RequestMethod.POST)
    public RestResponseDTO savePersonalTimes(@RequestBody List<ScreeningTimeAssingmentDTO> data){
        screeningService.savePersonalScreeningTime(data);
        return RestResponseDTO.buildSuccess();
    }

}
