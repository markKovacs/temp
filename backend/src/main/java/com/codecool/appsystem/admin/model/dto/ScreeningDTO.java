package com.codecool.appsystem.admin.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class ScreeningDTO extends RestResponseDTO{

    private Integer adminId;
    private String name;
    private String screeningDay;
    private String screeningGroupTime;
    private String screeningPersonalTime;
    private Boolean scheduleSignedBack;

}
