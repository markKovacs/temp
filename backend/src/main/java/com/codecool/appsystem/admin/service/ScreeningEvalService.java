package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ScreeningGrade;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScreeningEvalService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public void gradeScreening(ScreeningGrade grade) {

        Application application = userRepository.findOne(grade.getId()).getActiveApplication();
        application.setFinalResult(grade.getAccepted());

        if (Boolean.TRUE.equals(grade.getAccepted())) {
            application.getUser().setCanApply(false);
            userRepository.saveAndFlush(application.getUser());

            emailService.sendResultY(application.getUser());

        } else {
            emailService.sendResultN(application.getUser());
        }

        applicationRepository.save(application);
    }
}
