package com.codecool.appsystem.admin.model.dto.applicantDetails;

import com.codecool.appsystem.admin.model.dto.ApplicantsScreeningStepDTO;
import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicantDetailsDTO extends RestResponseDTO {

    private String givenName;
    private String familyName;
    private Integer dateOfBirth;
    private String phoneNumber;
    private String email;
    private Integer adminId;
    private Long timesApplied;
    private String location;
    private String comment;
    private Date processStartedAt;
    private List<TestResultDTO> testResults;
    private Date screeningGroupTime;
    private Date screeningPersonalTime;
    private Boolean scheduleSignedBack;
    private List<ApplicantsScreeningStepDTO> screeningSteps;
    private Boolean finalResult;
}
