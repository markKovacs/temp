package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduledActionsService {

    @Autowired
    private ApplicationRepository applicationRepository;

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

            Application application = testResult.getApplication();
            application.setActive(false);
            application.setFinalResult(false);
            application.setComment(testResult.getTest().getName() + " timed out after 24h at " + new Date());

            testResultRepository.saveAndFlush(testResult);

            applicationRepository.saveAndFlush(application);


        }
    }

}
