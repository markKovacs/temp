package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.MotivationDTO;
import com.codecool.appsystem.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MotivationsUtilService {


    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TestResultRepository testResultRepo;

    @Autowired
    private TestRepository testRepo;

    public List<MotivationDTO> getUngradedUsers(List<User> userList, String id) {

        List<MotivationDTO> motivation = new ArrayList<>();

        Test motivationTest = testRepo.findByMotivationVideoAndLocationId(true, id);

        for (User u: userList) {
            Application application = applicationRepository.findByApplicantIdAndActive(u.getId(), true);

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
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public void gradeMotivation(MotivationGrade motivationGrade){
        TestResult actualTestResult = testResultRepository.getOne(motivationGrade.getTestResultId());
        actualTestResult.setComment(motivationGrade.getComment());
        actualTestResult.setPassed(motivationGrade.getPassed());
        testResultRepository.save(actualTestResult);
    }


}
