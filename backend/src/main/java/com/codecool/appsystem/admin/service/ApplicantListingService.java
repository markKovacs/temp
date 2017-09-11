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

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ApplicationScreeningInfoRepository screeningInfoRepository;

    @Autowired
    private ApplicantsScreeningStepRepository screeningStepRepository;

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
                .map(application -> userRepository.findOne(application.getApplicantId()))
                .map(this::transform)
                .collect(Collectors.toList());

    }

    private ApplicantInfoDTO transform(User user){

        return ApplicantInfoDTO.builder()
                .name(user.getFullName())
                .id(user.getId())
                .blacklisted(user.getIsBlacklisted())
                .location(user.getLocationId())
                .attempts(getAttempts(user))
                .status(getStatus(user.getId()))
                .processStartedAt(getProcesssStartedAt(user))
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }

    private Date getProcesssStartedAt(User user){
        Application application = applicationRepository.findByApplicantIdAndActiveIsTrue(user.getId());
        return application == null ? null : application.getProcessStartedAt();
    }

    private String getStatus(Integer id) {

        Application application = applicationRepository.findByApplicantIdAndActiveIsTrue(id);

        if(application == null){
            return "-";
        }

        TestResult lastPassed = testResultRepository.findByApplicationId(application.getId())
                .stream()
                .sorted((o1, o2) -> o1.getStarted().before(o2.getStarted()) ? 1 : -1)
                .findFirst().orElse(null);

        if(lastPassed == null){
            return "-";
        }

        Test test = testRepository.findOne(lastPassed.getTestId());

        ApplicationScreeningInfo screeningInfo = screeningInfoRepository.findByApplicationId(application.getId());

        List<ApplicantsScreeningStep> screeningSteps = screeningStepRepository.findByApplicationId(application.getId());

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

        return test.getName();

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
