package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicantsScreeningStepRepository extends JpaRepository<ApplicantsScreeningStep, String> {

    ApplicantsScreeningStep findByStepId(String stepId);

    List<ApplicantsScreeningStep> findByApplicationId(String appId);

}
