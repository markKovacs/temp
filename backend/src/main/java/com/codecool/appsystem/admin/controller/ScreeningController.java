package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningTimeAssingmentDTO;
import com.codecool.appsystem.admin.service.ApplicationScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping("/api/screening/{id}")
    public ScreeningDTO getScreeningInfo(@PathVariable("id") Integer id){
        return screeningService.findOne(id);
    }

    @RequestMapping("/api/screening/candidates")
    public List<ScreeningDTO> getScreeningCandidates(@RequestParam String location){
        return screeningService.getCandidates(location);
    }

    @RequestMapping("/api/screening/assignmentcandidates")
    public List<ScreeningDTO> getScreeningAssignmentCandidates(@RequestParam String location){
        return screeningService.getAssignmentCandidates(location);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/api/screening/group", method = RequestMethod.POST)
    public void saveGroupTimes(@RequestBody List<ScreeningTimeAssingmentDTO> data){
        screeningService.saveGroupScreeningTime(data);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/api/screening/personal", method = RequestMethod.POST)
    public void savePersonalTimes(@RequestBody List<ScreeningTimeAssingmentDTO> data){
        screeningService.savePersonalScreeningTime(data);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/api/screening/sendmails", method = RequestMethod.POST)
    public void sendEmails(@RequestBody List<Integer> candidates){
        screeningService.sendMails(candidates);

    }

}
