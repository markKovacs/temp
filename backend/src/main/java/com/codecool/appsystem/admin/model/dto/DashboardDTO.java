package com.codecool.appsystem.admin.model.dto;


import com.codecool.appsystem.admin.model.Test;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//TODO pls add some javadoc, what is this?

public class DashboardDTO extends RestResponseDTO{

    private String id;
    private String title;
    private String description;
    private String estimatedMinutes;

    public static DashboardDTO fromTest(Test test){
        return DashboardDTO.builder()
                .id(test.getId())
                .title(test.getName())
                .description(test.getFormAsJson())
                .estimatedMinutes("10") //// FIXME: 2017.06.22. no such field in db
                .build();
    }

    
}
