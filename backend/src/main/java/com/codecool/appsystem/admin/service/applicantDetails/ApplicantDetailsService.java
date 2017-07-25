package com.codecool.appsystem.admin.service.applicantDetails;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ApplicantDetailsDTOBuilder;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.TestResultDTO;
import com.codecool.appsystem.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    @Autowired
    private ApplicationScreeningInfoRepository appScrInfoRepo;

    @Autowired
    private TestResultRepository testResRepository;

    @Autowired
    private TestRepository testRepo;


    public ApplicantDetailsDTO provideInfo(Integer id) {

        User user = userRepo.findByAdminId(id);

        Application application = applicationRepo.findByApplicantIdAndActive(user.getId(),true);

        ApplicationScreeningInfo appScrInf = appScrInfoRepo.findByApplicationId(application.getId());

        return provideDTO(user, application, appScrInf);
    }


    private ApplicantDetailsDTO provideDTO(User user, Application application, ApplicationScreeningInfo appScrInf) {

        return new ApplicantDetailsDTOBuilder()
                .fromUser(user)
                .timesApplied(applicationRepo.countByApplicantId(user.getId()))
                .fromApplication(application)
                .testResults(getTestInfo(application))
                .fromAppScrInfo(appScrInf)
                .build();

    }

    private List<TestResultDTO> getTestInfo(Application application) {

        List<TestResult> tests = testResRepository.findByApplicationId(application.getId());

        return tests
                .stream()
                .map(this::transformTestResult)
                .collect(Collectors.toList());

    }

    private TestResultDTO transformTestResult(TestResult testResult){

        Test test = testRepo.findOne(testResult.getTestId());

        TestResultDTO TDto = new TestResultDTO();
        TDto.setId(testResult.getId());
        TDto.setName(test.getName());
        TDto.setComment(testResult.getComment());
        TDto.setAnswer(testResult.getSavedAnswers());
        TDto.setIsPending(Boolean.TRUE.equals(testResult.getPassed() == null));
        TDto.setPassed(testResult.getPassed());
        TDto.setPoints(testResult.getPoints());
        TDto.setIsMotivation(test.getMotivationVideo());

        return TDto;

    }

}
