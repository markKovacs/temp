package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ScreeningGrade;
import com.codecool.appsystem.admin.model.User;
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

        User user = userRepository.findByAdminId(grade.getAdminId());

        Application application = applicationRepository.findByApplicantIdAndActive(user.getId(), true);
        application.setFinalResult(grade.getAccepted());

        if (Boolean.TRUE.equals(grade.getAccepted())) {
            user.setCanApply(false);
            userRepository.saveAndFlush(user);

            emailService.sendResultY(user);

        } else {
            emailService.sendResultN(user);
        }

        applicationRepository.save(application);
    }
}
