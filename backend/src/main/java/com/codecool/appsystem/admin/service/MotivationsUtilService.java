package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MotivationsUtilService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TestRepository testRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationScreeningInfoRepository applicationScreeningInfoRepository;

    public List<MotivationDTO> getUngradedUsers(List<User> userList, String id) {

        List<MotivationDTO> motivation = new ArrayList<>();

        Location location = locationRepository.findOne(id);

        Test motivationTest = testRepo.findByMotivationVideoIsTrueAndLocationAndEnabledIsTrue(location);

        if(motivationTest == null){
            log.warn("No motivation test found for location: {}", id);
            return Collections.emptyList();
        }

        for (User u: userList) {

            Application application = u.getActiveApplication();

            if(application == null){
                continue;
            }

            List<TestResult> testResults = application.getTestResults();

                for (TestResult testResult : testResults) {

                    if(testResult.getFinished() != null &&
                            !StringUtils.isEmpty(testResult.getSavedAnswers()) &&
                            Boolean.TRUE.equals(testResult.getTest().getMotivationVideo()) && testResult.getPassed() == null){

                        MotivationDTO userMotivation = new MotivationDTO();
                        userMotivation.setId(u.getId());

                        userMotivation.setIsVideo(checkMotivationText(testResult.getSavedAnswers()));
                        userMotivation.setName(u.getFullName());
                        userMotivation.setProcessStartedAt(application.getProcessStartedAt());

                        motivation.add(userMotivation);
                    }
                }
        }

        motivation.sort(Comparator.comparing(MotivationDTO::getProcessStartedAt));

        return motivation;

    }

    private Boolean checkMotivationText(String str){
        Pattern regex = Pattern.compile("^(http|https)\\:\\/\\/.+$");
        Matcher matcher = regex.matcher(str);
        return matcher.find();
    }

    public void gradeMotivation(MotivationGrade motivationGrade){

        TestResult actualTestResult = testResultRepository.getOne(motivationGrade.getTestResultId());

        actualTestResult.setComment(motivationGrade.getComment());
        if(motivationGrade.getPassed() != null){
            actualTestResult.setPassed(motivationGrade.getPassed());
        }

        Application application = actualTestResult.getApplication();
        User user = application.getUser();
        // accepted
        if(Boolean.TRUE.equals(motivationGrade.getPassed())){

            ApplicationScreeningInfo screeningInfo = new ApplicationScreeningInfo();
            screeningInfo.setApplication(application);
            actualTestResult.setPending(false);

            screeningInfo.setMapLocation(application.getLocation().getMapLocation());

            applicationScreeningInfoRepository.saveAndFlush(screeningInfo);

            emailService.sendMotivationSuccess(user);

            // failed
        } else if (Boolean.FALSE.equals(motivationGrade.getPassed())){
            actualTestResult.setPending(false);
            emailService.sendMotivationFailed(user);
        }

        // in other cases, just the comment got saved.

        testResultRepository.saveAndFlush(actualTestResult);
    }


}
