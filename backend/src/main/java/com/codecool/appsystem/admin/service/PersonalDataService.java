package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.PersonalData;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.PersonalDataDTO;
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

    public List<PersonalDataDTO> list() {

        List<PersonalData> original = personalDataRepository.findAll();
        List<PersonalDataDTO> list = original.stream()
                .filter(personalData -> {

                    User user = userRepository.findOne(personalData.getId());

                    Application application = user.getActiveApplication();

                    return application != null
                            && Boolean.TRUE.equals(application.getFinalResult())
                            && Boolean.TRUE.equals(application.getActive())
                            && !Boolean.TRUE.equals(user.getContractSigned());

                })
                .map(PersonalDataDTO::fromEntity)
                .map(personalData -> {
                    User user = userRepository.findOne(personalData.getId());
                    Application application = user.getActiveApplication();
                    personalData.setScreeningDate(application.getApplicationScreeningInfo().getScreeningGroupTime());
                    return personalData;
                })
                .collect(Collectors.toList());

        list.addAll(addFake(list));

        return list;



    }

    private List<PersonalDataDTO> addFake(List<PersonalDataDTO> original){
        List<Application> applications = applicationRepository.findByFinalResultIsTrueAndActiveIsTrue();

        return applications
                .stream()
                .filter(application -> {
                    for(PersonalDataDTO pd : original){
                        if(pd.getId().equals(application.getUser().getId())){
                            return false;
                        }
                    }
                    return true;
                })
                .map(application -> {
                    PersonalDataDTO pd = new PersonalDataDTO();
                    pd.setName(application.getUser().getFullName());
                    pd.setId(application.getUser().getId());
                    pd.setScreeningDate(application.getApplicationScreeningInfo().getScreeningGroupTime());
                    return pd;
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
