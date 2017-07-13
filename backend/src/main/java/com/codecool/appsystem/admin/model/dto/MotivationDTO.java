package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class MotivationDTO extends RestResponseDTO {

    private Integer adminId;
    private String name;
    private Boolean isVideo;
}
