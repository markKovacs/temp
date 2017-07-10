package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicantListingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TestResultRepository testResultRepo;

    private static final Map<Integer, String> upcomingTest  = createMap();

    private static final Map<Integer, String> createMap()
    {
        Map<Integer, String> upcomingTest  = new HashMap<>();
        upcomingTest.put(0, "Prerequisites");
        upcomingTest.put(1, "Introduction");
        upcomingTest.put(2, "English");
        upcomingTest.put(3, "Logic");
        upcomingTest.put(4, "Motivation");
        return upcomingTest;
    }


    public List<ApplicantInfoDTO> addApplicationData(String locationId) {


        List<User> userList = userRepository.findAllByLocationId(locationId);
        List<ApplicantInfoDTO> dtoResultList = new ArrayList<>();

        for (User user: userList) {

            ApplicantInfoDTO dto = new ApplicantInfoDTO();


            dto.setName(user.getFullName());
            dto.setAdminId(user.getAdminId());
            dto.setBlacklisted(user.getIsBlacklisted());
            dto.setLocation(user.getLocationId());

            dto.setAttempts(getAttempts(user));
            dto.setLastPassedTest(getLastPassedTest(user.getId()));
            dto.setSuccess(true);

            dtoResultList.add(dto);
        }

        return dtoResultList;
    }

    private String getLastPassedTest(String id) {

        Application application = applicationRepository.findByApplicantId(id);

        int noOfPassedTests;
        List<TestResult> applicantsTests;
        List<TestResult> passed = new ArrayList<>();

        applicantsTests = testResultRepo.findByApplicationId(application.getId());

        if (!applicantsTests.isEmpty()) {
            passed = applicantsTests.stream().filter(TestResult::isPassed).collect(Collectors.toList());
        }

        noOfPassedTests = passed.size();

        return upcomingTest.get(noOfPassedTests);
    }


    private long getAttempts(User user) {

        return applicationRepository.countByApplicantId(user.getId());
    }
}
