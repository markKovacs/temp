package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ScreeningStepEvaluationDTO;
import com.codecool.appsystem.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningEditService {

    @Autowired
    private ScreeningStepRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicantsScreeningStepRepository applicantsScreeningStepRepository;

    @Autowired
    private ApplicantsScreeningStepCriteriaRepository screeningStepCriteriaRepository;

    public List<ScreeningStep> findByLocationId(String locationId){
        return repository.findByLocationIdAndEnabled(locationId, true)
                .stream()
                .filter(screeningStep -> Boolean.TRUE.equals(screeningStep.getEnabled()))
                .collect(Collectors.toList());
    }

    public void saveScreening(List<ScreeningStep> data){
        for(ScreeningStep step : data){
            for(ScreeningStepCriteria criteria : step.getCriterias()){
                criteria.setScreeningStepId(step.getId());
            }
        }
        repository.save(data);
    }

    public ScreeningStepEvaluationDTO findForApplicant(Integer adminId, String stepId){

        User user = userRepository.findByAdminId(adminId);
        Application application = applicationRepository.findByApplicantIdAndActive(user.getId(), true);

        ScreeningStep step = repository.findOne(stepId);

        ApplicantsScreeningStep applicantsScreeningStep = applicantsScreeningStepRepository.findByStepId(stepId);

        if(applicantsScreeningStep == null){
            applicantsScreeningStep = new ApplicantsScreeningStep(stepId, application.getId());

            applicantsScreeningStep = applicantsScreeningStepRepository.saveAndFlush(applicantsScreeningStep);

            for(ScreeningStepCriteria criteria : step.getCriterias()){

                ApplicantsScreeningStepCriteria screeningStepCriteria = new ApplicantsScreeningStepCriteria();
                screeningStepCriteria.setApplicantsScreeningStepId(applicantsScreeningStep.getId());
                screeningStepCriteria.setCriteriaId(criteria.getId());

                screeningStepCriteria = screeningStepCriteriaRepository.saveAndFlush(screeningStepCriteria);

                applicantsScreeningStep.getCriterias().add(screeningStepCriteria);
            }

        }

        return ScreeningStepEvaluationDTO.builder()
                .screeningStep(applicantsScreeningStep)
                .name(user.getFullName())
                .age(LocalDate.now().getYear() - user.getBirthDate())
                .build();

    }

}
