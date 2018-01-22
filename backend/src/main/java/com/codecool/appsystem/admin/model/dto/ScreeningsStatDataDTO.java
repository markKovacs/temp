package com.codecool.appsystem.admin.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"day", "location"})
public class ScreeningsStatDataDTO {

    private Long day;
    private String location;
    private Integer countInvited = 0;
    private Integer countScheduleSignedBack = 0;
    private Integer countBeenToScreening = 0;
    private Integer countFinalResultY = 0;
    private Integer countFinalResultN = 0;

    public ScreeningsStatDataDTO() {

    }

    public ScreeningsStatDataDTO(Long day, String location) {
        this.day = day;
        this.location = location;
    }

}
