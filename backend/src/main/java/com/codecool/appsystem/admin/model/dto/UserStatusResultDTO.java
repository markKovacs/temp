package com.codecool.appsystem.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserStatusResultDTO extends UserStatusDTO{

    private String groupScreeningTime;
    private String personalScreeningTime;
    private String mapsLocation;

    @Builder
    public UserStatusResultDTO(String stepId, String state, String groupScreeningTime, String personalScreeningTime, String mapsLocation){
        super(stepId, state);
        this.groupScreeningTime = groupScreeningTime;
        this.personalScreeningTime = personalScreeningTime;
        this.mapsLocation = mapsLocation;
    }

    public static class UserStatusResultDTOBuilder extends UserStatusDTOBuilder{
        UserStatusResultDTOBuilder() {
            super();
        }
    }

}
