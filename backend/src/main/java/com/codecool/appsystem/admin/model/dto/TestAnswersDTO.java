package com.codecool.appsystem.admin.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestAnswersDTO {

    private String questionId;
    private List<String> answers;
}
