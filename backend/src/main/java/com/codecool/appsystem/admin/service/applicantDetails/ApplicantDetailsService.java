package com.codecool.appsystem.admin.service.applicantDetails;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ApplicantDetailsDTOBuilder;
import com.codecool.appsystem.admin.model.dto.ApplicantsScreeningStepDTO;
import com.codecool.appsystem.admin.model.dto.CriteriaDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.ApplicantDetailsDTO;
import com.codecool.appsystem.admin.model.dto.applicantDetails.TestResultDTO;
import com.codecool.appsystem.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private TestResultRepository testResRepository;

    @Autowired
    private TestRepository testRepo;

    @Autowired
    private ApplicantsScreeningStepRepository applicantsScreeningStepRepository;

    @Autowired
    private ScreeningStepRepository screeningStepRepository;

    @Autowired
    private ScreeningStepCriteriaRepository screeningStepCriteriaRepository;

    @Autowired
    private LocationRepository locationRepository;

    public ApplicantDetailsDTO provideInfo(Integer id) {

        User user = userRepo.findByAdminId(id);

        Application application = applicationRepo.findByApplicantIdAndActive(user.getId(),true);
        List<ApplicantsScreeningStep> applicantsScreeningSteps = applicantsScreeningStepRepository.findByApplicationId(application.getId());

        ApplicationScreeningInfo appScrInf = appScrInfoRepo.findByApplicationId(application.getId());
        return provideDTO(user, application, appScrInf, applicantsScreeningSteps);
    }

    public ApplicantDetailsDTO saveDates(Integer id, Map<String, Long> data) {

        //TODO send update email here

        User user = userRepo.findByAdminId(id);

        Location location = locationRepository.findOne(user.getLocationId());

        Application application = applicationRepo.findByApplicantIdAndActive(user.getId(),true);

        ApplicationScreeningInfo appScrInf = appScrInfoRepo.findByApplicationId(application.getId());

        if(appScrInf == null){
            appScrInf = new ApplicationScreeningInfo();
            appScrInf.setApplicationId(application.getId());
        }

        appScrInf.setScreeningGroupTime(new Date(data.get("group")));
        appScrInf.setScreeningPersonalTime(new Date(data.get("personal")));
        appScrInf.setMapLocation(location.getMapLocation());
        appScrInfoRepo.saveAndFlush(appScrInf);

        List<ApplicantsScreeningStep> applicantsScreeningSteps = applicantsScreeningStepRepository.findByApplicationId(application.getId());

        return provideDTO(user, application, appScrInf, applicantsScreeningSteps);

    }



    private ApplicantDetailsDTO provideDTO(User user, Application application, ApplicationScreeningInfo appScrInf, List<ApplicantsScreeningStep> applicantsScreeningSteps) {

        ApplicantDetailsDTO dto = new ApplicantDetailsDTOBuilder()
                .fromUser(user)
                .timesApplied(applicationRepo.countByApplicantId(user.getId()))
                .fromApplication(application)
                .testResults(getTestInfo(application))
                .fromAppScrInfo(appScrInf)
                .finalResult(application.getFinalResult())
                .build();

        if(applicantsScreeningSteps != null){
            dto.setScreeningSteps(processScreening(applicantsScreeningSteps));
        }

        return dto;

    }

    private List<ApplicantsScreeningStepDTO> processScreening(List<ApplicantsScreeningStep> steps){
        List<ApplicantsScreeningStepDTO> result = new ArrayList<>();
        for(ApplicantsScreeningStep step : steps){
            result.add(ApplicantsScreeningStepDTO.builder()
                    .comment(step.getComment())
                    .interviewer(step.getInterviewer())
                    .points(step.getPoints())
                    .status(step.getStatus())
                    .stepName(getNameForStep(step.getStepId()))
                    .criterias(processCriterias(step.getCriterias()))
                    .build());
        }
        return result;
    }

    private String getNameForStep(String id){
        return screeningStepRepository.findOne(id).getName();
    }

    private List<CriteriaDTO> processCriterias(List<ApplicantsScreeningStepCriteria> criterias){
        List<CriteriaDTO> result = new ArrayList<>();
        for(ApplicantsScreeningStepCriteria crit : criterias){
            result.add(CriteriaDTO.builder()
                    .comment(crit.getComment())
                    .points(crit.getPoints())
                    .status(crit.getStatus())
                    .criteriaName(getNameForCriteria(crit.getCriteriaId()))
                    .build()
            );
        }
        return result;
    }

    private String getNameForCriteria(String id){
        return screeningStepCriteriaRepository.findOne(id).getName();
    }

    private List<TestResultDTO> getTestInfo(Application application) {

        List<TestResult> tests = testResRepository.findByApplicationId(application.getId());
        return tests
                .stream()
                .map(this::transformTestResult)
                .sorted((o1, o2) -> o1.getSubmitted().before(o2.getSubmitted()) ? 1 : -1)
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
        TDto.setPercent(testResult.getPercent() == null ? null : testResult.getPercent().intValue());

        return TDto;

    }

}
