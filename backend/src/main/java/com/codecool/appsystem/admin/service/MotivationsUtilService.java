package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MotivationsUtilService {


    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TestResultRepository testResultRepo;

    @Autowired
    private TestRepository testRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationScreeningInfoRepository applicationScreeningInfoRepository;

    public List<MotivationDTO> getUngradedUsers(List<User> userList, String id) {

        List<MotivationDTO> motivation = new ArrayList<>();

        Test motivationTest = testRepo.findByMotivationVideoAndLocationId(true, id);

        if(motivationTest == null){
            log.warn("No motivation test found for location: {}", id);
            return Collections.emptyList();
        }

        for (User u: userList) {
            Application application = applicationRepository.findByApplicantIdAndActive(u.getId(), true);

            if(application == null){
                continue;
            }

            List<TestResult> testResults = testResultRepo.findByApplicationId(application.getId());

            if (testResults.size() == 4) {
                for (TestResult tr : testResults) {
                    if (tr.getTestId().equals(motivationTest.getId()) && tr.getPassed() == null){
                        MotivationDTO userMotivation = new MotivationDTO();

                        userMotivation.setAdminId(u.getAdminId());
                        userMotivation.setIsVideo(checkMotivationText(tr.getSavedAnswers()));
                        userMotivation.setName(u.getFullName());
                        motivation.add(userMotivation);
                    }
                }
            }
        }

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
        actualTestResult.setPassed(motivationGrade.getPassed());

        Application application = applicationRepository.findOne(actualTestResult.getApplicationId());
        User user = userRepository.findOne(application.getApplicantId());

        // accepted
        if(Boolean.TRUE.equals(motivationGrade.getPassed())){

            ApplicationScreeningInfo screeningInfo = new ApplicationScreeningInfo();
            screeningInfo.setApplicationId(application.getId());

            applicationScreeningInfoRepository.saveAndFlush(screeningInfo);


            emailService.sendMotivationSuccess(user);

            // failed
        } else if (Boolean.FALSE.equals(motivationGrade.getPassed())){
            emailService.sendMotivationFailed(user);
        }

        // in other cases, just the comment got saved.s

        testResultRepository.save(actualTestResult);
    }


}
