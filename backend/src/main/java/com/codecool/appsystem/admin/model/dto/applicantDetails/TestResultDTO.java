package com.codecool.appsystem.admin.model.dto.applicantDetails;

import com.codecool.appsystem.admin.model.dto.RestResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TestResultDTO extends RestResponseDTO{

    private String id;
    private String name;
    private Boolean passed;
    private int points;
    private String comment;
    private Boolean isPending;
    private String answer;
    private Boolean isMotivation;
    private Date started;
    private Date submitted;
    private int percent;
}
