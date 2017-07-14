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

    private final Map<Long, String> upcomingTest = new HashMap<>();

    public ApplicantListingService(){
        upcomingTest.put(0L, "Prerequisites");
        upcomingTest.put(1L, "Introduction");
        upcomingTest.put(2L, "English");
        upcomingTest.put(3L, "Logic");
        upcomingTest.put(4L, "Motivation");
    }

    public List<ApplicantInfoDTO> getApplicationData(String locationId) {

        List<User> userList = userRepository.findAllByLocationId(locationId);

        return userList
                .stream()
                .map(this::transform)
                .collect(Collectors.toList());

    }

    private ApplicantInfoDTO transform(User user){

        return ApplicantInfoDTO.builder()
                .name(user.getFullName())
                .adminId(user.getAdminId())
                .blacklisted(user.getIsBlacklisted())
                .location(user.getLocationId())
                .attempts(getAttempts(user))
                .lastPassedTest(getLastPassedTest(user.getId()))
                .build();

    }

    private String getLastPassedTest(String id) {

        Application application = applicationRepository.findByApplicantIdAndActive(id, Boolean.TRUE);

        List<TestResult> applicantsTests = testResultRepo.findByApplicationId(application.getId());

        long noOfPassedTests = applicantsTests
                    .stream()
                    .filter(testResult -> Boolean.TRUE.equals(testResult.getPassed()))
                    .count();

        return upcomingTest.get(noOfPassedTests);
    }


    /**
     * Counts all the application for the given user.
     * @param user
     * @return no. of applications in total
     */
    private long getAttempts(User user) {
        return applicationRepository.countByApplicantId(user.getId());
    }
}
