package com.codecool.appsystem.admin.model.dto;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScreeningStepEvaluationDTO {

    private String name;
    private Integer age;
    private ApplicantsScreeningStep screeningStep;
}
