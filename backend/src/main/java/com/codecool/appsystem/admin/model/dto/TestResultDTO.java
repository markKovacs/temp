package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class TestResultDTO {

    private String id;
    private String name;
    private Boolean passed;
    private Integer points;
    private String comment;
    private Boolean isPending;
    private String answer;
    private Boolean isMotivation;
    private Date started;
    private Date submitted;
    private Double percent;
}
