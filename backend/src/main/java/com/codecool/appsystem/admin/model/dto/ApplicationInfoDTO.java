package com.codecool.appsystem.admin.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ApplicationInfoDTO {

    private String comment;
    private Date processStartedAt;

    // ordered by Test's order in bundle
    private List<TestResultDTO> testResults;

    // screening info
    private Date screeningGroupTime;
    private Date screeningPersonalTime;
    private Boolean scheduleSignedBack;

    // screening evaluation info
    private List<ApplicantsScreeningStepDTO> screeningSteps;

    // and the final result
    private Boolean finalResult;

}
