package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.PersonalData;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApplicantAdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private ApplicationScreeningInfoRepository screeningInfoRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private ApplicantsScreeningStepRepository applicantsScreeningStepRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicantsScreeningStepCriteriaRepository applicantsScreeningStepCriteriaRepository;

    public void delete(Integer id){

        User user = userRepository.findOne(id);

        if(user == null){
            return;
        }

        for(Application application : user.getApplications()){

            if(application.getApplicationScreeningInfo() != null) {
                // delete screening info
                screeningInfoRepository.delete(application.getApplicationScreeningInfo());
            }

            // delete test results
            testResultRepository.delete(application.getTestResults());

            for(ApplicantsScreeningStep screeningStep : application.getScreeningSteps()){
                applicantsScreeningStepCriteriaRepository.delete(screeningStep.getCriteria());
            }

            applicantsScreeningStepRepository.delete(application.getScreeningSteps());

        }

        PersonalData personalData = personalDataRepository.findOne(user.getId());

        if(personalData != null) {
            personalDataRepository.delete(personalData);
        }

        applicationRepository.delete(user.getApplications());

        userRepository.delete(user);

    }

}
