package com.codecool.appsystem.admin.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionContent {

    private String id;
    private String questionContent;
    private String type;
    private Boolean graded;
    private List<QuestionOption> options;

}
