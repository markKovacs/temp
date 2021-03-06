package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
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
                    .filter(user -> user.getEmail().contains("@"))
                    .map(this::transform)
                    .collect(Collectors.toList());
        }

        List<Application> applications = applicationRepository.findByLocationIdAndActiveIsTrueAndFinalResultIsNull(locationId);

        return applications
                .stream()
                .filter(application -> application.getFinalResult() == null && !CollectionUtils.isEmpty(application.getTestResults()))
                .map(Application::getUser)
                .map(this::transform)
                .collect(Collectors.toList());

    }

    public List<ApplicantInfoDTO> getFinished(){
        List<Application> applications = applicationRepository.findByFinalResultIsNullAndFinalResultSentIsNull();

        return applications
                .stream()
                .filter(application -> !CollectionUtils.isEmpty(application.getScreeningSteps()) && !Boolean.TRUE.equals(application.getFinalResultSent()))
                .map(Application::getUser)
                .map(this::transform)
                .collect(Collectors.toList());
    }

    public List<ApplicantInfoDTO> getHired(){
        List<Application> applications = applicationRepository.findByFinalResultIsTrueAndActiveIsTrue();

        return applications
                .stream()
                .map(Application::getUser)
                .map(this::transform)
                .sorted(Comparator.comparing(ApplicantInfoDTO::getName))
                .collect(Collectors.toList());
    }

    private ApplicantInfoDTO transform(User user){

        return ApplicantInfoDTO.builder()
                .name(user.getFullName())
                .id(user.getId())
                .blacklisted(user.getIsBlacklisted())
                .location(user.getLocationId())
                .attempts(user.getApplications().size())
                .status(getStatus(user))
                .processStartedAt(getProcesssStartedAt(user))
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .finalResultSent(user.getActiveApplication() == null ? null : user.getActiveApplication().getFinalResultSent())
                .build();

    }

    private Date getProcesssStartedAt(User user){
        return user.getActiveApplication() == null
                ? null
                : user.getActiveApplication().getProcessStartedAt();
    }

    private String getStatus(User user) {

        Application application = user.getActiveApplication();

        if(application == null){
            return "-";
        }

        TestResult lastPassed = null;

        if(!CollectionUtils.isEmpty(application.getTestResults())){
            lastPassed = application.getTestResults().get(application.getTestResults().size() - 1);
        }

        if(lastPassed == null){
            return "-";
        }

        Test test = lastPassed.getTest();

        ApplicationScreeningInfo screeningInfo = application.getApplicationScreeningInfo();

        List<ApplicantsScreeningStep> screeningSteps = application.getScreeningSteps();

        if(screeningInfo != null && screeningInfo.getScheduleSignedBack() == null &&
                (screeningInfo.getScreeningGroupTime() != null || screeningInfo.getScreeningPersonalTime() != null)){
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

}
