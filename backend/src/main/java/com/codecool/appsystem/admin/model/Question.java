package com.codecool.appsystem.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private String id;
    private String surveyContent;
    private Integer estimatedTime;
    private String locationId;
    private String description;

    private String name;
    private Integer orderInBundle;
    private Integer maxPoints;
    private Integer threshold;
    private Boolean enabled;
    private Boolean motivationVideo;

    private List<QuestionContent> questions;


}
