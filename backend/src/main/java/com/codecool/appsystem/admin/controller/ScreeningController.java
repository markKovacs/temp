package com.codecool.appsystem.admin.controller;

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

    @RequestMapping(value = "/api/screening/list", method = RequestMethod.GET)
    public List<ScreeningDTO> getScreeningInfo(@RequestParam String location, @RequestParam("signedback") Boolean signedBack){
        return screeningService.find(location, signedBack);
    }

    @RequestMapping(value = "/api/screening/{id}", method = RequestMethod.GET)
    public ScreeningDTO getScreeningInfo(@PathVariable("id") Integer id){
        return screeningService.findOne(id);
    }

    @RequestMapping(value = "/api/screening/candidates", method = RequestMethod.GET)
    public List<ScreeningDTO> getScreeningCandidates(@RequestParam String location){
        return screeningService.getCandidates(location);
    }

    @RequestMapping(value = "/api/screening/assignmentcandidates", method = RequestMethod.GET)
    public List<ScreeningDTO> getScreeningAssignmentCandidates(@RequestParam String location){
        return screeningService.getAssignmentCandidates(location);
    }

    @RequestMapping(value = "/api/screening/group", method = RequestMethod.POST)
    public boolean saveGroupTimes(@RequestBody List<ScreeningTimeAssingmentDTO> data){
        screeningService.saveGroupScreeningTime(data);
        return true;
    }

    @RequestMapping(value = "/api/screening/personal", method = RequestMethod.POST)
    public boolean savePersonalTimes(@RequestBody List<ScreeningTimeAssingmentDTO> data){
        screeningService.savePersonalScreeningTime(data);
        return true;
    }

    @RequestMapping(value = "/api/screening/sendmails", method = RequestMethod.POST)
    public boolean sendEmails(@RequestBody List<Integer> candidates){
        screeningService.sendMails(candidates);
        return true;

    }

}
