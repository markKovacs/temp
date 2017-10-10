package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.PersonalData;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.repository.PersonalDataRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalDataService {

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private UserRepository userRepository;

    public List<PersonalData> list() {

        List<PersonalData> list = personalDataRepository.findAll();

        return list.stream()
                .filter(personalData -> {

                    User user = userRepository.findOne(personalData.getId());

                    Application application = user.getActiveApplication();

                    return Boolean.TRUE.equals(application.getFinalResult()) && !Boolean.TRUE.equals(user.getContractSigned());

                })
                .collect(Collectors.toList());

    }

}
