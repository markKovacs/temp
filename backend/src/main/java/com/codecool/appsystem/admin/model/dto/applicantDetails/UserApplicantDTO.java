package com.codecool.appsystem.admin.model.dto.applicantDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserApplicantDTO {

    private String givenName;
    private String familyName;
    private Integer dateOfBirth;
    private Integer adminId;
    private Long timesApplied;
    private String applyingTo;
    private Integer courseId;
    private Date processStarted;
}
