package com.codecool.appsystem.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantsScreeningStepDTO {

    private String stepName;
    private String interviewer;
    private Integer points;
    private String comment;
    private String status;
    private List<CriteriaDTO> criterias;

}

