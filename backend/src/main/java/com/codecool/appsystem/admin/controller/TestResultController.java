package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.service.TestResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/testResults")
public class TestResultController {
    @Autowired
    private TestResultService testResultService;

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public boolean deleteTestResult(@PathVariable("id") String id){
        testResultService.deleteTestResultById(id);
        return true;
    }
}
