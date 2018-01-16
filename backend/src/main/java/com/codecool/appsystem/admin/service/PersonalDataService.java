package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.PersonalData;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.PersonalDataRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<PersonalData> list() {

        List<PersonalData> list = personalDataRepository.findAll();

        return list.stream()
                .filter(personalData -> {

                    User user = userRepository.findOne(personalData.getId());

                    Application application = user.getActiveApplication();

                    return application != null
                            && Boolean.TRUE.equals(application.getFinalResult())
                            && Boolean.TRUE.equals(application.getActive())
                            && !Boolean.TRUE.equals(user.getContractSigned());

                })
                .collect(Collectors.toList());

    }

    public boolean setContractSigned(Integer id, String courseId){

        User user = userRepository.findOne(id);
        user.setContractSigned(true);
        Application application = user.getActiveApplication();
        application.setActive(false);
        user.setCourseId(courseId);

        applicationRepository.saveAndFlush(application);
        userRepository.saveAndFlush(user);

        // adding to other systems here

        return true;
    }

    public boolean setRejected(Integer id){

        User user = userRepository.findOne(id);
        user.setContractSigned(false);
        user.getActiveApplication().setActive(false);
        user.getActiveApplication().setComment("Rejected on " + new Date() + ", by: "
                + SecurityContextHolder.getContext().getAuthentication().getName());

        applicationRepository.saveAndFlush(user.getActiveApplication());
        userRepository.saveAndFlush(user);

        return true;

    }

}
