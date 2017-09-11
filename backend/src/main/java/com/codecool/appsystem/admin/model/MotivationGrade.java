package com.codecool.appsystem.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotivationGrade {

    private Integer id;
    private String testResultId;
    private Boolean passed;
    private String comment;
}
