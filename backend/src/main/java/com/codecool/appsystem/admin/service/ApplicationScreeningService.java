package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningTimeAssingmentDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EmailService emailService;

    public void saveGroupScreeningTime(List<ScreeningTimeAssingmentDTO> data){

        for(ScreeningTimeAssingmentDTO dto : data){

            log.debug("Saving group screening times: {}", dto);

            User user = userRepository.findOne(dto.getId());
            Application application = appRepository.findByApplicantIdAndActiveIsTrue(user.getId());

            ApplicationScreeningInfo screeningInfo = appScrRepo.findByApplicationId(application.getId());

            // the user has no assigned screening info
            if(screeningInfo == null){
                screeningInfo = new ApplicationScreeningInfo();

                Location location = locationRepository.findOne(application.getLocationId());
                screeningInfo.setMapLocation(location.getMapLocation());

                screeningInfo.setApplicationId(application.getId());
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
            Application application = appRepository.findByApplicantIdAndActiveIsTrue(user.getId());
            ApplicationScreeningInfo screeningInfo = appScrRepo.findByApplicationId(application.getId());

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
                .map(this::transformScreeningInfo)
                .collect(Collectors.toList());

    }

    public ScreeningDTO findOne(Integer id) {

        User user = userRepository.findOne(id);

        Application application =
                appRepository.findByApplicantIdAndActiveIsTrue(user.getId());

        if(application == null){
            return null;
        }

        ApplicationScreeningInfo appscr = appScrRepo.findByApplicationId(application.getId());
        return transformScreeningInfo(appscr);

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
        Test test = testRepository.findByMotivationVideoAndLocationId(Boolean.TRUE, application.getLocationId());

        TestResult testResult =
                testResultRepository.
                        findFirstByTestIdAndApplicationIdOrderByFinishedDesc(test.getId(), application.getId());

        return application.getFinalResult() == null && testResult != null && Boolean.TRUE.equals(testResult.getPassed());

    }

    private boolean isScreeningAssignmentCandidate(Application application){
        ApplicationScreeningInfo screeningInfo = appScrRepo.findByApplicationId(application.getId());
        return screeningInfo != null && !Boolean.TRUE.equals(screeningInfo.getScheduleSignedBack());
    }

    private ScreeningDTO createCandidate(Application application){

        ApplicationScreeningInfo screeningInfo = appScrRepo.findByApplicationId(application.getId());

        ScreeningDTO result = ScreeningDTO.builder()
                .id(findId(application.getId()))
                .name(findUserName(application.getId()))
                .age(getAge(application.getId()))
                .build();

        if(screeningInfo != null){
            result.setGroupTime(screeningInfo.getScreeningGroupTime());
            result.setPersonalTime(screeningInfo.getScreeningPersonalTime());
            result.setScheduleSignedBack(screeningInfo.getScheduleSignedBack());
        }
        return result;

    }

    private List<ApplicationScreeningInfo> findScreeningInfo(String locationId) {

        List<Application> applicationsByLocation =
                appRepository.findByLocationIdAndActiveIsTrue(locationId);

        List<ApplicationScreeningInfo> screeningInfo = new ArrayList<>();

        for (Application application : applicationsByLocation) {

            ApplicationScreeningInfo appscr =  appScrRepo.findByApplicationId(application.getId());

            if (appscr != null && application.getFinalResult() == null) {
                screeningInfo.add(appscr);
            }

        }

        return screeningInfo;

    }

    private ScreeningDTO transformScreeningInfo(ApplicationScreeningInfo asci) {
        Application application = appRepository.findOne(asci.getApplicationId());
        return ScreeningDTO
                .builder()
                .id(findId(asci.getApplicationId()))
                .groupTime(asci.getScreeningGroupTime())
                .personalTime(asci.getScreeningPersonalTime())
                .scheduleSignedBack(asci.getScheduleSignedBack())
                .name(findUserName(asci.getApplicationId()))
                .age(getAge(asci.getApplicationId()))
                .finalResult(application.getFinalResult())
                .build();

    }

    private String findUserName(String id) {
        Application application = appRepository.findOne(id);
        return userRepository.findOne(application.getApplicantId()).getFullName();
    }

    private Integer findId(String id) {
        Application application = appRepository.findOne(id);
        return userRepository.findOne(application.getApplicantId()).getId();
    }

    private Integer getAge(String id) {
        Application application = appRepository.findOne(id);
        User user = userRepository.findOne(application.getApplicantId());
        return LocalDate.now().getYear() - user.getBirthDate();
    }
}
