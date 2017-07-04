package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.ApplicationScreeningInfoRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ApplicationScreeningService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository appRepository;

    @Autowired
    private ApplicationScreeningInfoRepository appScrRepo;


    public List<ScreeningDTO> find(String locationId) {

        return buildScreeningDTO(locationId.toUpperCase());
    }


    private List<Application> applicationsByLocation(String locationId) {

        List<User> users = userRepository.findByLocationId(locationId);
        List<Application> applications = new ArrayList<>();

        for (User user: users) {
            Application application = appRepository.findByApplicantId(user.getId());
            applications.add(application);
        }

        return applications;
    }


    private List<ApplicationScreeningInfo> findScrInfo(String locationId){

        List<Application> applicationsByLocation = applicationsByLocation(locationId);
        List<ApplicationScreeningInfo> screeningInfo = new ArrayList<>();

        for (Application application: applicationsByLocation) {
            ApplicationScreeningInfo appScreeningInfo = appScrRepo.findByApplicationId(application.getId());
            screeningInfo.add(appScreeningInfo);
        }

        return screeningInfo;
    }

    private int findUserAdminId(String id){
        Application application = appRepository.findOne(id);
        return userRepository.findOne(application.getApplicantId()).getAdminId();
    }

    private String findUserName(String id){
        Application application = appRepository.findOne(id);
        return userRepository.findOne(application.getApplicantId()).getFullName();
    }

    private List<ScreeningDTO> buildScreeningDTO(String locationId) {
        List<ApplicationScreeningInfo> screeningInfo = findScrInfo(locationId);
        List<ScreeningDTO> dtoList = new ArrayList<>();

        for (ApplicationScreeningInfo asci: screeningInfo) {
            ScreeningDTO screeningDto = new ScreeningDTO();

            screeningDto.setScreeningPersonalTime(asci.getScreeningPersonalTime());
            screeningDto.setScreeningGroupTime(asci.getScreeningGroupTime());
            screeningDto.setScheduleSignedBack(asci.getScheduleSignedBack());
            screeningDto.setAdminId(findUserAdminId(asci.getApplicationId()));
            screeningDto.setName(findUserName(asci.getApplicationId()));
            screeningDto.setScreeningDay(asci.getScreeningDay());

            screeningDto.setSuccess(true);
            dtoList.add(screeningDto);
        }

        if (dtoList.isEmpty()) {
            ScreeningDTO notFound = new ScreeningDTO();
            notFound.setSuccess(false);
            notFound.setMessage("No appointments found");
            return Collections.singletonList(notFound);
        }


        return dtoList;
    }
}
