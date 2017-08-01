package com.codecool.appsystem.admin.model.dto;

import com.codecool.appsystem.admin.model.ApplicantsScreeningStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningStepEvaluationDTO {

    private String name;
    private Integer age;
    private ApplicantsScreeningStep screeningStep;
}
