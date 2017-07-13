package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.Test;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
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

    public List<MotivationDTO> getUngradedUsers(List<User> userList, String id) {

        List<MotivationDTO> motivation = new ArrayList<>();

        Test motivationTest = testRepo.findByMotivationVideoAndLocationId(true, id);

        for (User u: userList) {
            Application application = applicationRepository.findByApplicantIdAndActive(u.getId(), true);

            List<TestResult> testResults = testResultRepo.findByApplicationId(application.getId());

            if (testResults.size() == 4) {
                for (TestResult tr : testResults) {
                    if (tr.getTestId().equals(motivationTest.getId()) && tr.getIsPending()){
                        MotivationDTO userMotivation = new MotivationDTO();

                        userMotivation.setAdminId(u.getAdminId());
                        userMotivation.setIsVideo(tr.getMotivationText().length() < 30);
                        userMotivation.setName(u.getFullName());
                        motivation.add(userMotivation);
                    }
                }
            }
        }

        if (!motivation.isEmpty()) {
            return motivation;
        }

        return Collections.emptyList();

    }
}
