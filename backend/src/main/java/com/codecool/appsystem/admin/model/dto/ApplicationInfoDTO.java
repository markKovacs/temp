package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationInfoDTO {

    private String id;
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

    private Boolean active;
    // and the final result
    private Boolean finalResult;

}
