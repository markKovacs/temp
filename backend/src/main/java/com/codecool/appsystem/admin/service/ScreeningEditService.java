package com.codecool.appsystem.admin.service;

import com.codecool.appsystem.admin.model.ScreeningStep;
import com.codecool.appsystem.admin.model.ScreeningStepCriteria;
import com.codecool.appsystem.admin.repository.ScreeningStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreeningEditService {

    @Autowired
    private ScreeningStepRepository repository;

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

}
