package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningTimeAssingmentDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private TestResultRepository testResultRepository;

    @Autowired
    private TestRepository testRepository;

    public void saveGroupScreeningTime(List<ScreeningTimeAssingmentDTO> data){

        for(ScreeningTimeAssingmentDTO dto : data){
            // truncate all our saved dates to hh:mm
            Date groupTime = Date.from(
                    Instant.ofEpochMilli(dto.getTime().getTime()).truncatedTo(ChronoUnit.MINUTES)
            );

            List<ApplicationScreeningInfo> byScreeningGroupTime = appScrRepo.findByScreeningGroupTime(groupTime);

            // null out all the group dates for the given time
            byScreeningGroupTime
                    .forEach(applicationScreeningInfo -> applicationScreeningInfo.setScreeningGroupTime(null));

        }

        for(ScreeningTimeAssingmentDTO dto : data){
            User user = userRepository.findByAdminId(dto.getId());
            Application application = appRepository.findByApplicantIdAndActive(user.getId(), Boolean.TRUE);

            ApplicationScreeningInfo screeningInfo = appScrRepo.findByApplicationId(application.getId());

            // the user has no assigned screening info
            if(screeningInfo == null){
                screeningInfo = new ApplicationScreeningInfo();
                screeningInfo.setApplicationId(application.getId());
            }

            Date groupTime = Date.from(
                    Instant.ofEpochMilli(dto.getTime().getTime()).truncatedTo(ChronoUnit.MINUTES)
            );

            screeningInfo.setScreeningGroupTime(groupTime);

            appScrRepo.saveAndFlush(screeningInfo);

        }


    }

    public void savePersonalScreeningTime(List<ScreeningTimeAssingmentDTO> data){

        for(ScreeningTimeAssingmentDTO dto : data){
            // truncate all our saved dates to hh:mm
            Date personalTime = Date.from(
                    Instant.ofEpochMilli(dto.getTime().getTime()).truncatedTo(ChronoUnit.MINUTES)
            );

            User user = userRepository.findByAdminId(dto.getId());
            Application application = appRepository.findByApplicantIdAndActive(user.getId(), Boolean.TRUE);
            ApplicationScreeningInfo screeningInfo = appScrRepo.findByApplicationId(application.getId());

            screeningInfo.setScreeningPersonalTime(personalTime);

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
                .map(this::transformScreeningInfo)
                .collect(Collectors.toList());

    }

    public List<ScreeningDTO> getCandidates(String locationId) {

        return appRepository.findByLocationId(locationId)
                .stream()
                .filter(this::isScreeningCandidate)
                .map(this::createCandidate)
                .collect(Collectors.toList());

    }

    private boolean isScreeningCandidate(Application application){
        Test test = testRepository.findByMotivationVideoAndLocationId(Boolean.TRUE, application.getLocationId());

        TestResult testResult =
                testResultRepository.
                        findFirstByTestIdAndApplicationIdOrderByFinishedDesc(test.getId(), application.getId());

        return testResult != null && Boolean.TRUE.equals(testResult.getPassed());

    }

    private ScreeningDTO createCandidate(Application application){

        ApplicationScreeningInfo screeningInfo = appScrRepo.findByApplicationId(application.getId());

        ScreeningDTO result = ScreeningDTO.builder()
                .adminId(findUserAdminId(application.getId()))
                .name(findUserName(application.getId()))
                .age(getAge(application.getId()))
                .build();

        if(screeningInfo != null){
            result.setGroupTime(screeningInfo.getScreeningGroupTime());
            result.setPersonalTime(screeningInfo.getScreeningPersonalTime());
        }
        return result;

    }

    private List<ApplicationScreeningInfo> findScreeningInfo(String locationId) {

        List<Application> applicationsByLocation =
                appRepository.findByLocationIdAndActive(locationId, Boolean.TRUE);

        List<ApplicationScreeningInfo> screeningInfo = new ArrayList<>();

        for (Application application : applicationsByLocation) {

            screeningInfo.add(
                    appScrRepo.findByApplicationId(application.getId())
            );

        }

        return screeningInfo;

    }

    private ScreeningDTO transformScreeningInfo(ApplicationScreeningInfo asci) {

        String groupTime =
                new SimpleDateFormat("yyyy.MM.dd HH:mm").format(asci.getScreeningGroupTime());

        String personalTime =
                new SimpleDateFormat("yyyy.MM.dd HH:mm").format(asci.getScreeningPersonalTime());

        return ScreeningDTO
                .builder()
                .groupTime(asci.getScreeningGroupTime())
                .personalTime(asci.getScreeningPersonalTime())
                .scheduleSignedBack(asci.getScheduleSignedBack())
                .adminId(findUserAdminId(asci.getApplicationId()))
                .name(findUserName(asci.getApplicationId()))
                .age(getAge(asci.getApplicationId()))
                .build();

    }

    private ApplicationScreeningInfo getForApplication(Application application) {
        return appScrRepo.findByApplicationId(application.getId());
    }

    private int findUserAdminId(String id) {
        Application application = appRepository.findOne(id);
        return userRepository.findOne(application.getApplicantId()).getAdminId();
    }

    private String findUserName(String id) {
        Application application = appRepository.findOne(id);
        return userRepository.findOne(application.getApplicantId()).getFullName();
    }

    private Integer getAge(String id) {
        Application application = appRepository.findOne(id);
        User user = userRepository.findOne(application.getApplicantId());
        return LocalDate.now().getYear() - user.getBirthDate();
    }
}
