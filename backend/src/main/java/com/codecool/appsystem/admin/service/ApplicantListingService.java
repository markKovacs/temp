package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ApplicantInfoDTO;
import com.codecool.appsystem.admin.repository.*;
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

    @Autowired
    private ApplicationScreeningInfoRepository screeningInfoRepository;

    public List<ApplicantInfoDTO> getApplicationData(String locationId) {

        List<Application> applications = applicationRepository.findByLocationIdAndActive(locationId, true);

        return applications
                .stream()
                .map(application -> userRepository.findOne(application.getApplicantId()))
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

        ApplicationScreeningInfo screeningInfo = screeningInfoRepository.findByApplicationId(application.getId());

        if(screeningInfo != null && screeningInfo.getScheduleSignedBack() == null){
            return "Screening times assigned";
        }

        if(screeningInfo != null && Boolean.TRUE.equals(screeningInfo.getScheduleSignedBack())){
            return "Schedule accepted";
        }

        if(Boolean.TRUE.equals(test.getMotivationVideo()) && lastPassed.getPassed() == null){
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
