package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.MotivationGrade;
import com.codecool.appsystem.admin.service.MotivationsUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/grademotivation")
public class MotivationController {


    @Autowired
    private MotivationsUtilService motivationsUtilService;

    @RequestMapping(method = RequestMethod.POST)
    public boolean applicantsByLocation(@RequestBody MotivationGrade motivationGrade) {
        motivationsUtilService.gradeMotivation(motivationGrade);
        return true;
    }
}
