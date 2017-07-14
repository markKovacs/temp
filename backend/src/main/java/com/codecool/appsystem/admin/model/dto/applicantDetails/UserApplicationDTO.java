package com.codecool.appsystem.admin.model.dto.applicantDetails;

import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class UserApplicationDTO extends RestResponseDTO{

    private String givenName;
    private String familyName;
    private Integer dateOfBirth;
    private Integer adminId;
    private Long timesApplied;
    private String applyingTo;
    private Integer courseId;
    private Date processStarted;
}
