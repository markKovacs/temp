package com.codecool.appsystem.admin.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode(callSuper = false)
public class ScreeningDTO extends RestResponseDTO{

    private Integer id;
    private String name;
    private Date groupTime;
    private Date personalTime;
    private Boolean scheduleSignedBack;
    private Integer age;
    private String gender;
    private Boolean finalResult;

    private Boolean afterTwoDays;

}
