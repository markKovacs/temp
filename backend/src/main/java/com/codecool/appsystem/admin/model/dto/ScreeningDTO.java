package com.codecool.appsystem.admin.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class ScreeningDTO extends RestResponseDTO{

    private Integer adminId;
    private String name;
    private Map<String,Object> screeningInfo;
}
