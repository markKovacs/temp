package com.codecool.appsystem.admin.model.dto;


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
public class UserStatusDTO extends RestResponseDTO {

    String stepId;
    String state;
    String finalPercent;


    public UserStatusDTO(String stepId, String state) {
        this.stepId = stepId;
        this.state = state;
    }

}
