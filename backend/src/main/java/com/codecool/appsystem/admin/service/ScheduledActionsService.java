package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduledActionsService {

    @Autowired
    private TestResultRepository testResultRepository;

    @Scheduled(cron = "* 0 * * * MON-FRI")
    public void timeOutTests(){
        List<String> timedOutIds = testResultRepository.getTimedOutTests();
        for(String id : timedOutIds){

            TestResult testResult = testResultRepository.findOne(id);
            testResult.setPoints(0);
            testResult.setPercent(0d);
            testResult.setComment("Timed out after 24h");
            testResult.setFinished(new Date());
            testResult.setPassed(false);

            testResultRepository.saveAndFlush(testResult);

        }
    }

}
