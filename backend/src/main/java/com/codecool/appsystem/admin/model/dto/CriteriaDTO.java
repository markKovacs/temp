package com.codecool.appsystem.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaDTO {

    private String criteriaName;
    private Integer points;
    private String comment;
    private String status;

}
