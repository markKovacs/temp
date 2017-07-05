package com.codecool.appsystem.admin.model.dto.applicantDetails;

import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicantDetailsDTO extends RestResponseDTO {

    private String givenName;
    private String familyName;
    private Integer dateOfBirth;
    private Integer adminId;
    private Long timesApplied;
    private String applyingTo;
    //(from User Table)

    private Integer courseId;
    private Date processStartedAt;
    // applications table

    private List<TestResultDTO> testResults;
    // testResults

    private String screeningDay;
    private String screeningGroupTime;
    private String screeningPersonalTime;
    private Boolean scheduleSignedBack;
    // appScrInfo Table
}
