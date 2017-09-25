package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    public List<ApplicantInfoDTO> getApplicationData(String locationId, Boolean all) {

        if(Boolean.TRUE.equals(all)){
            return userRepository.findByLocationId(locationId)
                    .stream()
                    .map(this::transform)
                    .collect(Collectors.toList());
        }

        List<Application> applications = applicationRepository.findByLocationIdAndActiveIsTrue(locationId);

        return applications
                .stream()
                .filter(application -> !Boolean.FALSE.equals(application.getFinalResult()))
                .map(Application::getUser)
                .map(this::transform)
                .collect(Collectors.toList());

    }

    private ApplicantInfoDTO transform(User user){

        return ApplicantInfoDTO.builder()
                .name(user.getFullName())
                .id(user.getId())
                .blacklisted(user.getIsBlacklisted())
                .location(user.getLocationId())
                .attempts(getTimesApplied(user))
                .status(getStatus(user))
                .processStartedAt(getProcesssStartedAt(user))
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }

    private Date getProcesssStartedAt(User user){
        Application application = user.getApplication();
        return application == null ? null : application.getProcessStartedAt();
    }

    private String getStatus(User user) {

        Application application = user.getApplication();

        if(application == null){
            return "-";
        }

        TestResult lastPassed = application.getTestResults() == null
                ? null
                : application.getTestResults().get(application.getTestResults().size() - 1);

        if(lastPassed == null){
            return "-";
        }

        Test test = lastPassed.getTest();

        ApplicationScreeningInfo screeningInfo = application.getApplicationScreeningInfo();

        List<ApplicantsScreeningStep> screeningSteps = application.getScreeningSteps();

        if(screeningInfo != null && screeningInfo.getScheduleSignedBack() == null){
            return "Screening times assigned";
        }

        if(screeningInfo != null && Boolean.TRUE.equals(screeningInfo.getScheduleSignedBack())
                && CollectionUtils.isEmpty(screeningSteps)){
            return "Schedule accepted";
        }

        if(!CollectionUtils.isEmpty(screeningSteps) && application.getFinalResult() == null){
            return "Screening";
        }

        if(application.getFinalResult() != null){
            if(application.getFinalResult()){
                return "Final result: Y";
            }
            return "Final result: N";
        }

        // if the test is still pending
        if(lastPassed.getFinished() == null){
            return test.getName() + " in progress";
        }

        return test.getName();

    }

    private int getTimesApplied(User user){
        int val = applicationRepository.findByUserAndActiveIsNot(user, true).size();
        if(user.getApplication() != null){
            val++;
        }
        return val;
    }

}
