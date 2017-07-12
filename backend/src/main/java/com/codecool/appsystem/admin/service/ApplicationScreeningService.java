package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.ApplicationScreeningInfoRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationScreeningService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository appRepository;

    @Autowired
    private ApplicationScreeningInfoRepository appScrRepo;

    /**
     * Returns all the current application screening info
     * for the given location
     * @param locationId
     * @return
     */
    public List<ScreeningDTO> find(String locationId) {

        List<ApplicationScreeningInfo> screeningInfo = findScrInfo(locationId);

        return screeningInfo
                .stream()
                .map(this::transformScreeningInfo)
                .collect(Collectors.toList());
    }

    private ScreeningDTO transformScreeningInfo(ApplicationScreeningInfo asci){
        ScreeningDTO screeningDto = new ScreeningDTO();

        screeningDto.setScreeningPersonalTime(asci.getScreeningPersonalTime());
        screeningDto.setScreeningGroupTime(asci.getScreeningGroupTime());
        screeningDto.setScheduleSignedBack(asci.getScheduleSignedBack());
        screeningDto.setAdminId(findUserAdminId(asci.getApplicationId()));
        screeningDto.setName(findUserName(asci.getApplicationId()));
        screeningDto.setScreeningDay(asci.getScreeningDay());

        return screeningDto;
    }

    private List<ApplicationScreeningInfo> findScrInfo(String locationId){

        List<Application> applicationsByLocation = appRepository.findByLocationId(locationId);
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
}
