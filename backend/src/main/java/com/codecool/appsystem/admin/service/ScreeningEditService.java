package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.config.security.AuthenticatedUser;
import com.codecool.appsystem.admin.model.*;
import com.codecool.appsystem.admin.model.dto.ApplicantsScreeningStepDTO;
import com.codecool.appsystem.admin.model.dto.CriteriaDTO;
import com.codecool.appsystem.admin.model.dto.ScreeningStepEvaluationDTO;
import com.codecool.appsystem.admin.repository.ApplicantsScreeningStepCriteriaRepository;
import com.codecool.appsystem.admin.repository.ApplicantsScreeningStepRepository;
import com.codecool.appsystem.admin.repository.ScreeningStepRepository;
import com.codecool.appsystem.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
            for(ScreeningStepCriteria criteria : step.getCriteria()){
                criteria.setScreeningStepId(step.getId());
            }
        }
        repository.save(data);
    }

    public List<ScreeningStepEvaluationDTO> getSteps(Integer id){

        Application application = userRepository.findOne(id).getActiveApplication();

        return application.getScreeningSteps().stream()
                .map(step -> ScreeningStepEvaluationDTO.builder()
                        .screeningStep(step)
                        .build())
                .collect(Collectors.toList());

    }

    public ScreeningStepEvaluationDTO findForApplicant(Integer id, String stepId){

        Application application = userRepository.findOne(id).getActiveApplication();

        ScreeningStep step = repository.findOne(stepId);

        ApplicantsScreeningStep applicantsScreeningStep =
                application.getScreeningSteps()
                    .stream()
                    .filter(applicantsScreeningStep1 -> applicantsScreeningStep1.getStep().getId().equals(stepId))
                        .findFirst()
                        .orElse(null);

        if(applicantsScreeningStep == null){
            applicantsScreeningStep = new ApplicantsScreeningStep(step, application);

            applicantsScreeningStep = applicantsScreeningStepRepository.saveAndFlush(applicantsScreeningStep);

            for(ScreeningStepCriteria criteria : step.getCriteria()){

                ApplicantsScreeningStepCriteria screeningStepCriteria = new ApplicantsScreeningStepCriteria();
                screeningStepCriteria.setApplicantsScreeningStepId(applicantsScreeningStep.getId());
                screeningStepCriteria.setCriteria(criteria);

                screeningStepCriteria = screeningStepCriteriaRepository.saveAndFlush(screeningStepCriteria);

                applicantsScreeningStep.getCriteria().add(screeningStepCriteria);
            }

        }

        return ScreeningStepEvaluationDTO.builder()
                .screeningStep(applicantsScreeningStep)
                .name(application.getUser().getFullName())
                .age(LocalDate.now().getYear() - application.getUser().getBirthDate())
                .build();

    }


    public void saveEvaluation(ApplicantsScreeningStepDTO data){

        ApplicantsScreeningStep step = applicantsScreeningStepRepository.findOne(data.getId());
        step.setInterviewer(((AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName());

        step.setPoints(data.getPoints());
        step.setComment(data.getComment());
        step.setStatus(data.getStatus());

        for(ApplicantsScreeningStepCriteria crit : step.getCriteria()){
            for(CriteriaDTO dto : data.getCriteria()){
                if(crit.getId().equals(dto.getId())){
                    crit.setPoints(dto.getPoints());
                    crit.setComment(dto.getComment());
                    crit.setStatus(dto.getStatus());
                }
            }
        }

        applicantsScreeningStepRepository.saveAndFlush(step);
    }

}
