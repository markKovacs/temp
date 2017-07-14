package com.codecool.appsystem.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuestionOption {

    private String id;
    private String optionContent;

    //@JsonIgnore
    private Boolean isCorrect;
}
