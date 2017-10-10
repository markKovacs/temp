package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.*;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.ApplicationScreeningInfoRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApplicantDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ApplicationScreeningInfoRepository appScrInfoRepo;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EmailService emailService;

    public ApplicantDetailsDTO provideInfo(Integer id) {

        User user = userRepo.findOne(id);

        ApplicantDetailsDTO result = ApplicantDetailsDTO.builder()
                .id(user.getId())
                .givenName(user.getGivenName())
                .familyName(user.getFamilyName())
                .dateOfBirth(user.getBirthDate())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .timesApplied(user.getApplications().size())
                .applications(new ArrayList<>())
                .build();

        for(Application app : user.getApplications()){
            result.getApplications().add(getApplicationInfo(app));
        }

        result.getApplications().sort((o1, o2) ->
        {
            if(o1.getProcessStartedAt() == null){
                return 1;
            }
            return o1.getProcessStartedAt().compareTo(o2.getProcessStartedAt());
        });


        return result;
    }

    public void saveDates(Integer id, Map<String, Long> data) {

        User user = userRepo.findOne(id);

        Application application = user.getActiveApplication();

        ApplicationScreeningInfo appScrInf = application.getApplicationScreeningInfo();

        if(appScrInf == null){
            appScrInf = new ApplicationScreeningInfo();
            appScrInf.setApplication(application);
        }

        boolean datesChanged = false;

        Date groupTime = new Date(data.get("group"));
        if(!groupTime.equals(appScrInf.getScreeningGroupTime())) {
            appScrInf.setScreeningGroupTime(groupTime);
            datesChanged = true;
        }

        Date personalTime = new Date(data.get("personal"));

        if(!personalTime.equals(appScrInf.getScreeningPersonalTime())){
            appScrInf.setScreeningPersonalTime(personalTime);
            datesChanged = true;
        }

        appScrInf.setMapLocation(application.getLocation().getMapLocation());
        appScrInfoRepo.saveAndFlush(appScrInf);


        if(datesChanged){
            emailService.sendScreeningTimesAssigned(user, appScrInf);
        }

    }

    public void terminate(Integer id){

        User user = userRepo.findOne(id);

        if(user != null){

            Application application = user.getActiveApplication();

            if(application != null){
                application.setActive(false);

                application.setComment(
                        "Terminated by admin: " +
                                SecurityContextHolder.getContext().getAuthentication().getName() +
                                ", on: " + new Date());

                application.setFinalResult(false);

                applicationRepository.saveAndFlush(application);

            }

        }

    }

    private ApplicationInfoDTO getApplicationInfo(Application application){

        if(application == null){
            return null;
        }

        ApplicationInfoDTO dto = ApplicationInfoDTO.builder()
                .comment(application.getComment())
                .processStartedAt(application.getProcessStartedAt())
                .testResults(transform(application.getTestResults()))
                .finalResult(application.getFinalResult())
                .active(application.getActive())
                .build();

        if(application.getApplicationScreeningInfo() != null){
            dto.setScreeningGroupTime(application.getApplicationScreeningInfo().getScreeningGroupTime());
            dto.setScreeningPersonalTime(application.getApplicationScreeningInfo().getScreeningPersonalTime());
            dto.setScheduleSignedBack(application.getApplicationScreeningInfo().getScheduleSignedBack());
        }

        if(!CollectionUtils.isEmpty(application.getScreeningSteps())){
            dto.setScreeningSteps(processScreening(application.getScreeningSteps()));
        }

        if(!CollectionUtils.isEmpty(dto.getTestResults())) {
            Collections.reverse(dto.getTestResults());
        }

        return dto;

    }

    private List<TestResultDTO> transform(List<TestResult> testResults){

        if(CollectionUtils.isEmpty(testResults)){
            return null;
        }

        return testResults.stream()
                .map(testResult ->
                        TestResultDTO.builder()
                        .id(testResult.getId())
                        .name(testResult.getTest().getName())
                        .passed(testResult.getPassed())
                        .points(testResult.getPoints())
                        .comment(testResult.getComment())
                        .isPending(Boolean.TRUE.equals(testResult.getPassed() == null))
                        .answer(
                                Boolean.TRUE.equals(testResult.getTest().getMotivationVideo()) ?
                                    testResult.getSavedAnswers() : null
                        )
                        .isMotivation(testResult.getTest().getMotivationVideo())
                        .started(testResult.getStarted())
                        .submitted(testResult.getFinished())
                        .percent(testResult.getPercent())
                        .build())
                .collect(Collectors.toList());

    }

    private List<ApplicantsScreeningStepDTO> processScreening(List<ApplicantsScreeningStep> steps){

        return steps
                .stream()
                .map(step -> ApplicantsScreeningStepDTO.builder()
                        .name(step.getStep().getName())
                        .comment(step.getComment())
                        .interviewer(step.getInterviewer())
                        .points(step.getPoints())
                        .status(step.getStatus())
                        .criteria(processCriterias(step.getCriteria()))
                        .build())
                .collect(Collectors.toList());

    }

    private List<CriteriaDTO> processCriterias(List<ApplicantsScreeningStepCriteria> criterias){
        List<CriteriaDTO> result = new ArrayList<>();
        for(ApplicantsScreeningStepCriteria crit : criterias){
            result.add(
                CriteriaDTO.builder()
                        .name(crit.getCriteria().getName())
                    .comment(crit.getComment())
                    .points(crit.getPoints())
                    .status(crit.getStatus())
                    .build()
            );
        }
        return result;
    }

}
