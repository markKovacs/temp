package com.codecool.appsystem.admin.repository;

import com.codecool.appsystem.admin.model.ScreeningStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningStepRepository extends JpaRepository<ScreeningStep, String> {

    List<ScreeningStep> findByLocationIdAndEnabled(String locationId, Boolean enabled);

}
