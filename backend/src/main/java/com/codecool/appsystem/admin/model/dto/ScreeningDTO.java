package com.codecool.appsystem.admin.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class ScreeningDTO extends RestResponseDTO{

    private Integer adminId;
    private String name;
    private Date groupTime;
    private Date personalTime;
    private Boolean scheduleSignedBack;
    private Integer age;
    private String gender;

}
