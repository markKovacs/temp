package com.codecool.appsystem.admin.service.applicantDetails;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ApplicantDetailsDTOBuilder;
import com.codecool.appsystem.admin.model.dto.ApplicantsScreeningStepDTO;
import com.codecool.appsystem.admin.model.dto.CriteriaDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.TestResultDTO;
import com.codecool.appsystem.admin.repository.*;
import com.codecool.appsystem.admin.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicantDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    @Autowired
    private ApplicationScreeningInfoRepository appScrInfoRepo;

    @Autowired
    private EmailService emailService;

    public ApplicantDetailsDTO provideInfo(Integer id) {

        User user = userRepo.findOne(id);

        Application application = user.getApplication();

        if(application == null) {
            application = applicationRepo.findLast(user.getId());
        }

        return provideDTO(user, application);
    }

    public ApplicantDetailsDTO saveDates(Integer id, Map<String, Long> data) {

        User user = userRepo.findOne(id);

        Application application = user.getApplication();

        Location location = application.getLocation();


        ApplicationScreeningInfo appScrInf = application.getApplicationScreeningInfo();

        if(appScrInf == null){
            appScrInf = new ApplicationScreeningInfo();
            appScrInf.setApplication(application);
        }

        boolean datesChanged = false;

        Date groupTime = new Date(data.get("group"));
        if(!groupTime.equals(appScrInf.getScreeningGroupTime())) {
            appScrInf.setScreeningGroupTime(groupTime);
            datesChanged = true;
        }

        Date personalTime = new Date(data.get("personal"));

        if(!personalTime.equals(appScrInf.getScreeningPersonalTime())){
            appScrInf.setScreeningPersonalTime(personalTime);
            datesChanged = true;
        }

        appScrInf.setMapLocation(location.getMapLocation());
        appScrInfoRepo.saveAndFlush(appScrInf);


        if(datesChanged){
            emailService.sendScreeningTimesAssigned(user, appScrInf);
        }

        return provideDTO(user, application);

    }



    private ApplicantDetailsDTO provideDTO(User user, Application application) {

        return new ApplicantDetailsDTOBuilder()
                .fromUser(user)
                .timesApplied(applicationRepo.countByApplicantId(user.getId()))
                .fromApplication(application)
                .testResults(getTestInfo(application))
                .finalResult(application.getFinalResult())
                .screeningSteps(processScreening(application.getScreeningSteps()))
                .build();

    }

    private List<ApplicantsScreeningStepDTO> processScreening(List<ApplicantsScreeningStep> steps){

        return steps
                .stream()
                .map(step -> ApplicantsScreeningStepDTO.builder()
                        .comment(step.getComment())
                        .interviewer(step.getInterviewer())
                        .points(step.getPoints())
                        .status(step.getStatus())
                        .stepName(step.getStep().getName())
                        .criterias(processCriterias(step.getCriterias()))
                        .build())
                .collect(Collectors.toList());

    }

    private List<CriteriaDTO> processCriterias(List<ApplicantsScreeningStepCriteria> criterias){
        List<CriteriaDTO> result = new ArrayList<>();
        for(ApplicantsScreeningStepCriteria crit : criterias){
            result.add(CriteriaDTO.builder()
                    .comment(crit.getComment())
                    .points(crit.getPoints())
                    .status(crit.getStatus())
                    .criteriaName(crit.getCriteria().getName())
                    .build()
            );
        }
        return result;
    }

    private List<TestResultDTO> getTestInfo(Application application) {

        List<TestResult> tests = application.getTestResults();

        return tests
                .stream()
                .sorted(Comparator.comparing(t -> t.getTest().getOrderInBundle()))
                .map(this::transformTestResult)
                .collect(Collectors.toList());

    }

    private TestResultDTO transformTestResult(TestResult testResult){

        Test test = testResult.getTest();


        TestResultDTO TDto = new TestResultDTO();
        TDto.setId(testResult.getId());
        TDto.setName(test.getName());
        TDto.setComment(testResult.getComment());
        TDto.setAnswer(testResult.getSavedAnswers());
        TDto.setIsPending(Boolean.TRUE.equals(testResult.getPassed() == null));
        TDto.setPassed(testResult.getPassed());
        if(testResult.getPoints() != null) {
            TDto.setPoints(testResult.getPoints());
        }
        TDto.setIsMotivation(test.getMotivationVideo());
        TDto.setSubmitted(testResult.getFinished());
        TDto.setStarted(testResult.getStarted());
        TDto.setPercent(testResult.getPercent() == null ? 0 : testResult.getPercent().intValue());

        return TDto;

    }

}
