package com.codecool.appsystem.admin.model.dto;

import com.codecool.appsystem.admin.model.QuestionContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TestDTO {

    private String id;
    private String name;
    private String locationId;
    private Integer orderInBundle;
    private String description;
    private String surveyContent;
    private Integer maxPoints;
    private Integer threshold;
    private Boolean enabled;
    private Integer estimatedTime;
    private Boolean motivationVideo;
    protected List<QuestionContent> questions;


}
