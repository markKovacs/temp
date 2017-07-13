package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.Test;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MotivationsUtilService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TestResultRepository testResultRepo;

    @Autowired
    private TestRepository testRepo;

    @Autowired
    private UserRepository userRepository;

    public List<User> getUngradedUsers(List<User> userList, String id) {

        List<Application> applications = new ArrayList<>();

        Test motivationTest = testRepo.findByMotivationVideoAndLocationId(true, id);

        for (User u: userList) {
            Application application = applicationRepository.findByApplicantIdAndActive(u.getId(), true);

            List<TestResult> testResults = testResultRepo.findByApplicationId(application.getId());

            if (testResults.size() == 4) {
                for (TestResult tr : testResults) {
                    if (tr.getTestId().equals(motivationTest.getId()) && tr.getIsPending()){
                        Application applicant = applicationRepository.findOne(tr.getApplicationId());
                        applications.add(applicant);
                    }
                }
            }

        }

        List<User> result = new ArrayList<>();

        if (!applications.isEmpty()) {
            for (Application app : applications) {
                User user = userRepository.findOne(app.getApplicantId());
                result.add(user);
            }
            return result;
        }

        return Collections.emptyList();

    }
}
