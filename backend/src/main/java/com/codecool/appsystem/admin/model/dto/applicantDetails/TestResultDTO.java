package com.codecool.appsystem.admin.model.dto.applicantDetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TestResultDTO {

    private String name;
    private boolean passed;
    private int points;
    private String comment;
    private String motivation;
}
