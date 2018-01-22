package com.codecool.appsystem.admin.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"day", "location"})
public class TestsStatDataDTO {

    private Long day;
    private String location;
    private String test;
    private Integer countStarted = 0;
    private Integer countSuccess = 0;

    public TestsStatDataDTO() {

    }

    public TestsStatDataDTO(Long day, String location, String test) {
        this.day = day;
        this.location = location;
        this.test = test;
    }

}
