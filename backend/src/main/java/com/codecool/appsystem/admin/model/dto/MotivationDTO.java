package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class MotivationDTO extends RestResponseDTO {

    private Integer id;
    private String name;
    private Boolean isVideo;
    private Date processStartedAt;
    private Boolean hasComment;

}
