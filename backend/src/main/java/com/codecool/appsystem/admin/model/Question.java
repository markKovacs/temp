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
    private List<QuestionContent> questions;
    private Integer estimatedTime;
    private String formUrl;
    private String locationId;
    private String description;

}
