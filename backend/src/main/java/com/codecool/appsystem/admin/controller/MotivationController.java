package com.codecool.appsystem.admin.controller;

import com.codecool.appsystem.admin.model.MotivationGrade;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.codecool.appsystem.admin.repository.TestResultRepository;
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
    private TestResultRepository testResultRepository;


    @RequestMapping(method = RequestMethod.POST)
    public RestResponseDTO applicantsByLocation(@RequestBody MotivationGrade motivationGrade) {

        TestResult actualTestResult = testResultRepository.getOne(motivationGrade.getId());
        actualTestResult.setComment(motivationGrade.getComment());
        actualTestResult.setPassed(motivationGrade.getPassed());
        testResultRepository.save(actualTestResult);
        log.info("actual test Result: " +actualTestResult);

        return RestResponseDTO.buildSuccess();

    }
}
