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

    @Autowired
    private ApplicantsScreeningStepRepository applicantsScreeningStepRepository;


    public ApplicantDetailsDTO provideInfo(Integer id) {

        User user = userRepo.findByAdminId(id);


        Application application = applicationRepo.findByApplicantIdAndActive(user.getId(),true);
        List<ApplicantsScreeningStep> applicantsScreeningSteps = applicantsScreeningStepRepository.findByApplicationId(application.getId());

        ApplicationScreeningInfo appScrInf = appScrInfoRepo.findByApplicationId(application.getId());
        return provideDTO(user, application, appScrInf, applicantsScreeningSteps);
    }


    private ApplicantDetailsDTO provideDTO(User user, Application application, ApplicationScreeningInfo appScrInf, List<ApplicantsScreeningStep> applicantsScreeningSteps) {

        return new ApplicantDetailsDTOBuilder()
                .fromUser(user)
                .timesApplied(applicationRepo.countByApplicantId(user.getId()))
                .fromApplication(application)
                .testResults(getTestInfo(application))
                .fromAppScrInfo(appScrInf)
                .fromScreening(applicantsScreeningSteps)
                .build();

    }

    private List<TestResultDTO> getTestInfo(Application application) {

        List<TestResult> tests = testResRepository.findByApplicationId(application.getId());

        return tests
                .stream()
                .map(this::transformTestResult)
                .sorted((o1, o2) -> {
                    return o1.getSubmitted().before(o2.getSubmitted()) ? 1 : -1;
                })
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
        TDto.setSubmitted(testResult.getFinished());
        TDto.setPercent(testResult.getPercent().intValue());

        return TDto;

    }

}
