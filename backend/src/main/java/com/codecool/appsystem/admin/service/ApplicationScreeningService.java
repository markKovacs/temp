package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.Application;
import com.codecool.appsystem.admin.model.ApplicationScreeningInfo;
import com.codecool.appsystem.admin.model.TestResult;
import com.codecool.appsystem.admin.model.User;
import com.codecool.appsystem.admin.model.dto.ScreeningDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningTimeAssingmentDTO;
import com.codecool.appsystem.admin.repository.ApplicationRepository;
import com.codecool.appsystem.admin.repository.ApplicationScreeningInfoRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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

            if(dto.getTime() == null){
                continue;
            }

            log.debug("Saving group screening times: {}", dto);

            User user = userRepository.findOne(dto.getId());

            ApplicationScreeningInfo screeningInfo = user.getActiveApplication().getApplicationScreeningInfo();

            // the user has no assigned screening info
            if(screeningInfo == null){
                screeningInfo = new ApplicationScreeningInfo();
                screeningInfo.setMapLocation(user.getActiveApplication().getLocation().getMapLocation());
                screeningInfo.setApplication(user.getActiveApplication());
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

            if(dto.getTime() == null){
                continue;
            }

            log.debug("Saving personal times: {}", data);

            // truncate all our saved dates to hh:mm
            Date personalTime = Date.from(
                    Instant.ofEpochMilli(dto.getTime().getTime()).truncatedTo(ChronoUnit.MINUTES)
            );

            User user = userRepository.findOne(dto.getId());
            ApplicationScreeningInfo screeningInfo = user.getActiveApplication().getApplicationScreeningInfo();

            if(!personalTime.equals(screeningInfo.getScreeningPersonalTime())) {
                screeningInfo.setScreeningPersonalTime(personalTime);
            }

            appScrRepo.saveAndFlush(screeningInfo);
        }

    }

    public void sendMails(List<Integer> ids){

        for(Integer id : ids){
            User user = userRepository.findOne(id);

            if(user.getActiveApplication().getApplicationScreeningInfo().getScreeningGroupTime() == null ||
                    user.getActiveApplication().getApplicationScreeningInfo().getScreeningPersonalTime() == null ||
                    user.getActiveApplication().getApplicationScreeningInfo().getScheduleSignedBack() != null){

                log.warn("Failed to send screening times email to user {}, missing info: {}",
                        id, user.getActiveApplication().getApplicationScreeningInfo());

                continue;

            }

            emailService.sendScreeningTimesAssigned(user, user.getActiveApplication().getApplicationScreeningInfo());
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
                .filter(screeningInfo -> signedBack == null ||
                        signedBack.equals(screeningInfo.getScheduleSignedBack()))
                .map(ApplicationScreeningInfo::getApplication)
                .map(this::transformScreeningInfo)
                .sorted(Comparator.comparing(ScreeningDTO::getGroupTime))
                .collect(Collectors.toList());

    }

    public ScreeningDTO findOne(Integer id) {

        User user = userRepository.findOne(id);


        if(user.getActiveApplication() == null || user.getActiveApplication().getApplicationScreeningInfo() == null){
            return null;
        }

        return transformScreeningInfo(user.getActiveApplication());

    }

    public List<ScreeningDTO> getCandidates(String locationId) {

        return appRepository.findByLocationId(locationId)
                .stream()
                .filter(this::isScreeningCandidate)
                .map(this::createCandidate)
                .sorted((o1, o2) -> {
                    if(o1.getGroupTime() == null && o2.getGroupTime() == null){
                        return o1.getName().compareTo(o2.getName());
                    }
                    if(o1.getGroupTime() == null){
                        return 1;
                    }
                    if(o2.getGroupTime() == null){
                        return -1;
                    }
                    return o1.getGroupTime().compareTo(o2.getGroupTime());
                })
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

        boolean testCriteria = application.getFinalResult() == null && Boolean.TRUE.equals(last.getPassed());

        if(!testCriteria){
            return false;
        }

        // if not time is given, display it
        if(application.getApplicationScreeningInfo().getScreeningGroupTime() == null){
            return true;
        }

        return !LocalDate.now()
                .atTime(22, 59, 0)
                .toInstant(ZoneOffset.UTC)
                .isAfter(application.getApplicationScreeningInfo().getScreeningGroupTime().toInstant());


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
                appRepository.findByLocationIdAndActiveIsTrueAndFinalResultIsNull(locationId);

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
