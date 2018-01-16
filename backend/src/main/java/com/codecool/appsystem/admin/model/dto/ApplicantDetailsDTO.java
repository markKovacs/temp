package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicantDetailsDTO {

    // user info
    private Integer id;
    private String givenName;
    private String familyName;
    private Integer dateOfBirth;
    private String phoneNumber;
    private String email;
    private Integer timesApplied;
    private String location;

    private boolean finalResult;
    private String courseId;

    // info about applications
    // the first one is always the most recent one
    private List<ApplicationInfoDTO> applications = new ArrayList<>();
}
