package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.Test;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.TestRepository;
import com.codecool.appsystem.admin.repository.TestResultRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicantListingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestRepository testRepository;

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
                .status(getStatus(user.getId()))
                .processStartedAt(getProcesssStartedAt(user))
                .email(user.getId())
                .build();

    }

    private Date getProcesssStartedAt(User user){
        return applicationRepository.findByApplicantIdAndActive(user.getId(), true).getProcessStartedAt();
    }

    private String getStatus(String id) {

        Application application = applicationRepository.findByApplicantIdAndActive(id, Boolean.TRUE);

        TestResult lastPassed = testResultRepository.findByApplicationId(application.getId())
                .stream()
                .sorted((o1, o2) -> o1.getStarted().before(o2.getStarted()) ? 1 : -1)
                .findFirst().orElse(null);

        if(lastPassed == null){
            return "Not started yet";
        }

        Test test = testRepository.findOne(lastPassed.getTestId());

        if(test.getMotivationVideo() && lastPassed.getPassed() == null){
            return test.getName() + " submitted";
        }

        if(Boolean.FALSE.equals(lastPassed.getPassed())){
            return test.getName() + " failed";
        }

        return test.getName() + " passed";

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
