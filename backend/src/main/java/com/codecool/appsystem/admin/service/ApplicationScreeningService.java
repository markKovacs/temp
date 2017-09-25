package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningTimeAssingmentDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationScreeningService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository appRepository;

    @Autowired
    private ApplicationScreeningInfoRepository appScrRepo;

    @Autowired
    private EmailService emailService;

    public void saveGroupScreeningTime(List<ScreeningTimeAssingmentDTO> data){

        for(ScreeningTimeAssingmentDTO dto : data){

            log.debug("Saving group screening times: {}", dto);

            User user = userRepository.findOne(dto.getId());

            ApplicationScreeningInfo screeningInfo = user.getApplication().getApplicationScreeningInfo();

            // the user has no assigned screening info
            if(screeningInfo == null){
                screeningInfo = new ApplicationScreeningInfo();
                screeningInfo.setMapLocation(user.getApplication().getLocation().getMapLocation());
                screeningInfo.setApplication(user.getApplication());
            }

            Date groupTime = Date.from(
                    Instant.ofEpochMilli(dto.getTime().getTime()).truncatedTo(ChronoUnit.MINUTES)
            );

            if(!groupTime.equals(screeningInfo.getScreeningGroupTime())){
                screeningInfo.setScreeningGroupTime(groupTime);
            }

            appScrRepo.saveAndFlush(screeningInfo);

        }


    }

    public void savePersonalScreeningTime(List<ScreeningTimeAssingmentDTO> data){

        for(ScreeningTimeAssingmentDTO dto : data){
            // truncate all our saved dates to hh:mm
            Date personalTime = Date.from(
                    Instant.ofEpochMilli(dto.getTime().getTime()).truncatedTo(ChronoUnit.MINUTES)
            );

            User user = userRepository.findOne(dto.getId());
            ApplicationScreeningInfo screeningInfo = user.getApplication().getApplicationScreeningInfo();

            if(!personalTime.equals(screeningInfo.getScreeningPersonalTime())) {
                screeningInfo.setScreeningPersonalTime(personalTime);
                emailService.sendScreeningTimesAssigned(user, screeningInfo);
            }

            appScrRepo.saveAndFlush(screeningInfo);
        }

    }

    /**
     * Returns all the current application screening info
     * for the given location
     *
     * @param locationId
     * @return
     */
    public List<ScreeningDTO> find(String locationId, Boolean signedBack) {

        return findScreeningInfo(locationId)
                .stream()
                .filter(Objects::nonNull)
                .filter(applicationScreeningInfo ->
                        signedBack == null ||
                                signedBack.equals(applicationScreeningInfo.getScheduleSignedBack()))
                .map(ApplicationScreeningInfo::getApplication)
                .map(this::transformScreeningInfo)
                .collect(Collectors.toList());

    }

    public ScreeningDTO findOne(Integer id) {

        User user = userRepository.findOne(id);


        if(user.getApplication() == null || user.getApplication().getApplicationScreeningInfo() == null){
            return null;
        }

        return transformScreeningInfo(user.getApplication());

    }

    public List<ScreeningDTO> getCandidates(String locationId) {

        return appRepository.findByLocationId(locationId)
                .stream()
                .filter(this::isScreeningCandidate)
                .map(this::createCandidate)
                .collect(Collectors.toList());

    }

    public List<ScreeningDTO> getAssignmentCandidates(String locationId) {

        return appRepository.findByLocationId(locationId)
                .stream()
                .filter(this::isScreeningAssignmentCandidate)
                .map(this::createCandidate)
                .collect(Collectors.toList());

    }

    private boolean isScreeningCandidate(Application application){

        if(CollectionUtils.isEmpty(application.getTestResults())){
            return false;
        }

        TestResult last = application.getTestResults().get(application.getTestResults().size() - 1);

        if(!Boolean.TRUE.equals(last.getTest().getMotivationVideo())){
            return false;
        }

        return application.getFinalResult() == null && Boolean.TRUE.equals(last.getPassed());

    }

    private boolean isScreeningAssignmentCandidate(Application application){
        return application.getApplicationScreeningInfo() != null
                && !Boolean.TRUE.equals(application.getApplicationScreeningInfo().getScheduleSignedBack());
    }

    private ScreeningDTO createCandidate(Application application){

        ScreeningDTO result = ScreeningDTO.builder()
                .id(application.getUser().getId())
                .name(application.getUser().getFullName())
                .age(LocalDate.now().getYear() - application.getUser().getBirthDate())
                .build();

        if(application.getApplicationScreeningInfo() != null){
            result.setGroupTime(application.getApplicationScreeningInfo().getScreeningGroupTime());
            result.setPersonalTime(application.getApplicationScreeningInfo().getScreeningPersonalTime());
            result.setScheduleSignedBack(application.getApplicationScreeningInfo().getScheduleSignedBack());
        }
        return result;

    }

    private List<ApplicationScreeningInfo> findScreeningInfo(String locationId) {

        List<Application> applicationsByLocation =
                appRepository.findByLocationIdAndActiveIsTrue(locationId);

        return applicationsByLocation
                .stream()
                .filter(application -> application.getFinalResult() == null)
                .map(Application::getApplicationScreeningInfo)
                .collect(Collectors.toList());

    }

    private ScreeningDTO transformScreeningInfo(Application application) {

        return ScreeningDTO
                .builder()
                .id(application.getUser().getId())
                .groupTime(application.getApplicationScreeningInfo().getScreeningGroupTime())
                .personalTime(application.getApplicationScreeningInfo().getScreeningPersonalTime())
                .scheduleSignedBack(application.getApplicationScreeningInfo().getScheduleSignedBack())
                .name(application.getUser().getFullName())
                .age(LocalDate.now().getYear() - application.getUser().getBirthDate())
                .finalResult(application.getFinalResult())
                .build();

    }

}
