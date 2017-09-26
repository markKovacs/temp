package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.MotivationGrade;
import com.codecool.appsystem.admin.service.MotivationsUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/grademotivation")
public class MotivationController {


    @Autowired
    private MotivationsUtilService motivationsUtilService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void applicantsByLocation(@RequestBody MotivationGrade motivationGrade) {
        motivationsUtilService.gradeMotivation(motivationGrade);
    }
}
